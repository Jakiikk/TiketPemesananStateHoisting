package com.example.pemesanantiketpart2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Locale


// =========================================================
// COLOR PALETTE - HIJAU TOSCA
// =========================================================

val ToscaPrimary = Color(0xFF16A085)
val ToscaLight = Color(0xFF1ABC9C)
val ToscaDark = Color(0xFF12806A)

val AppBackground = Color(0xFFF3FAF8)
val CardBackground = Color.White

val MainText = Color(0xFF263238)
val SecondaryText = Color(0xFF78909C)

val SuccessGreen = Color(0xFF16845F)
val ErrorRed = Color(0xFFE74C3C)


// =========================================================
// MAIN ACTIVITY
// =========================================================

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            TicketApp()
        }
    }
}


// =========================================================
// APP
// =========================================================

@Composable
fun TicketApp() {

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppBackground
        ) {

            TicketScreen()
        }
    }
}


// =========================================================
// PARENT / STATE HOISTING
// =========================================================

@Composable
fun TicketScreen() {

    // =====================================================
    // STATE YANG DIKELOLA PARENT
    // =====================================================

    var ticketPrice by rememberSaveable {
        mutableIntStateOf(25_000)
    }

    var ticketQuantity by rememberSaveable {
        mutableIntStateOf(1)
    }

    var buyerName by rememberSaveable {
        mutableStateOf("")
    }

    var status by rememberSaveable {
        mutableStateOf("Nama masih kosong")
    }

    var isProcessing by rememberSaveable {
        mutableStateOf(false)
    }

    var orderRequested by rememberSaveable {
        mutableStateOf(false)
    }


    // =====================================================
    // LAUNCHED EFFECT
    // Proses pemesanan selama 5 detik
    // =====================================================

    LaunchedEffect(orderRequested) {

        if (orderRequested) {

            isProcessing = true
            status = "Memproses pesanan..."

            delay(5000)

            isProcessing = false
            status = "Tiket telah dipesan"

            orderRequested = false
        }
    }


    // =====================================================
    // TOTAL HARGA
    // =====================================================

    val totalPrice = ticketPrice * ticketQuantity


    // =====================================================
    // UI
    // =====================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {

        HeaderSection()


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 14.dp,
                    bottom = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // =================================================
            // NAMA PEMBELI
            // =================================================

            BuyerNameCard(
                name = buyerName,
                onNameChange = {
                    buyerName = it

                    if (it.isBlank()) {
                        status = "Nama masih kosong"
                    } else if (!isProcessing) {
                        status = "Silakan pesan tiket"
                    }
                }
            )


            // =================================================
            // HARGA TIKET
            // =================================================

            TicketPriceCard(
                price = ticketPrice
            )


            // =================================================
            // JUMLAH TIKET
            // =================================================

            QuantityCard(
                quantity = ticketQuantity,

                onMinusClick = {
                    if (ticketQuantity > 1) {
                        ticketQuantity--
                    }
                },

                onPlusClick = {
                    ticketQuantity++
                }
            )


            // =================================================
            // TOTAL
            // =================================================

            TotalCard(
                total = totalPrice
            )


            // =================================================
            // PESAN TIKET
            // =================================================

            Button(
                onClick = {

                    if (buyerName.isBlank()) {

                        status = "Nama masih kosong"

                    } else {

                        orderRequested = true
                    }
                },

                enabled = !isProcessing,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),

                shape = RoundedCornerShape(12.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = ToscaPrimary,
                    contentColor = Color.White
                )
            ) {

                Icon(
                    imageVector = Icons.Default.ConfirmationNumber,
                    contentDescription = "Pesan Tiket",
                    modifier = Modifier.size(21.dp)
                )

                Spacer(
                    modifier = Modifier.size(8.dp)
                )

                Text(
                    text = if (isProcessing) {
                        "Memproses..."
                    } else {
                        "PESAN TIKET"
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            // =================================================
            // STATUS
            // =================================================

            StatusCard(
                status = status,
                isProcessing = isProcessing
            )
        }
    }
}


// =========================================================
// HEADER
// =========================================================

@Composable
fun HeaderSection() {

    val gradient = Brush.verticalGradient(
        colors = listOf(
            ToscaLight,
            ToscaPrimary
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 28.dp,
                    bottomEnd = 28.dp
                )
            )
            .background(gradient)
            .padding(
                top = 50.dp,
                bottom = 25.dp,
                start = 20.dp,
                end = 20.dp
            ),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(
                    RoundedCornerShape(18.dp)
                )
                .background(
                    Color.White.copy(alpha = 0.18f)
                ),

            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Default.ConfirmationNumber,
                contentDescription = "Ticket",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        Text(
            text = "Pemesanan Tiket",
            color = Color.White,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )


        Spacer(
            modifier = Modifier.height(3.dp)
        )


        Text(
            text = "Pesan tiket dengan mudah!",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp
        )
    }
}


// =========================================================
// NAMA PEMBELI
// =========================================================

@Composable
fun BuyerNameCard(
    name: String,
    onNameChange: (String) -> Unit
) {

    InfoCard {

        Text(
            text = "Nama Pembeli Tiket",
            color = MainText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        androidx.compose.material3.OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Masukkan nama Anda"
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
    }
}


// =========================================================
// HARGA TIKET
// =========================================================

@Composable
fun TicketPriceCard(
    price: Int
) {

    InfoCard {

        Text(
            text = "Harga Tiket",
            color = MainText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = formatRupiah(price),
            color = ToscaPrimary,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "per tiket",
            color = SecondaryText,
            fontSize = 13.sp
        )
    }
}


// =========================================================
// JUMLAH TIKET
// =========================================================

@Composable
fun QuantityCard(
    quantity: Int,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit
) {

    InfoCard {

        Text(
            text = "Jumlah Tiket",
            color = MainText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            QuantityButton(
                icon = Icons.Default.Remove,
                contentDescription = "Kurangi tiket",
                onClick = onMinusClick
            )


            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
                    .height(50.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .background(
                        Color(0xFFE8F5F2)
                    ),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = quantity.toString(),
                    color = MainText,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            QuantityButton(
                icon = Icons.Default.Add,
                contentDescription = "Tambah tiket",
                onClick = onPlusClick
            )
        }
    }
}


// =========================================================
// BUTTON JUMLAH
// =========================================================

@Composable
fun QuantityButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {

    IconButton(
        onClick = onClick,

        modifier = Modifier
            .size(50.dp)
            .clip(
                RoundedCornerShape(14.dp)
            )
            .background(ToscaPrimary)
    ) {

        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(26.dp)
        )
    }
}


// =========================================================
// TOTAL
// =========================================================

@Composable
fun TotalCard(
    total: Int
) {

    InfoCard {

        Text(
            text = "Total Pembayaran",
            color = MainText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = formatRupiah(total),
            color = SuccessGreen,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


// =========================================================
// STATUS
// =========================================================

@Composable
fun StatusCard(
    status: String,
    isProcessing: Boolean
) {

    val backgroundColor = when {

        status == "Tiket telah dipesan" ->
            Color(0xFFE5F6ED)

        status == "Nama masih kosong" ->
            Color(0xFFFFEEEE)

        isProcessing ->
            Color(0xFFE6F7F4)

        else ->
            Color(0xFFEFF5F4)
    }


    val textColor = when {

        status == "Tiket telah dipesan" ->
            SuccessGreen

        status == "Nama masih kosong" ->
            ErrorRed

        else ->
            ToscaDark
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(12.dp)
            )
            .background(backgroundColor)
            .padding(
                horizontal = 16.dp,
                vertical = 13.dp
            )
    ) {

        Text(
            text = "Status:",
            color = SecondaryText,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Text(
            text = status,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


// =========================================================
// GENERIC CARD
// =========================================================

@Composable
fun InfoCard(
    content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(CardBackground)
            .padding(
                horizontal = 16.dp,
                vertical = 13.dp
            ),

        content = content
    )
}


// =========================================================
// FORMAT RUPIAH
// =========================================================

fun formatRupiah(value: Int): String {

    return "Rp" + String.format(
        Locale.US,
        "%,d",
        value
    ).replace(
        ",",
        "."
    )
}