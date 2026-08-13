package com.surgex.app.ui.screens.rider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.surgex.app.ui.theme.SurgeBlack
import com.surgex.app.ui.theme.SurgeGrey
import com.surgex.app.ui.theme.SurgeSurface
import com.surgex.app.ui.theme.SurgeWhite

@Composable
fun RiderPaymentScreen(
    total: Double = 169.80,
    onPaymentSuccess: () -> Unit,
    onCancel: () -> Unit
) {

    var selectedMethod by remember {
        mutableStateOf("Cash")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SurgeBlack)
            .padding(22.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Payment",
            color = SurgeWhite,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Complete your SurgeX trip",
            color = SurgeGrey,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(25.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SurgeSurface,
            shape = RoundedCornerShape(20.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "TOTAL",
                    color = SurgeGrey,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "R${"%.2f".format(total)}",
                    color = SurgeWhite,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "PAYMENT METHOD",
            color = SurgeGrey,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        PaymentMethod(
            name = "Cash",
            selected = selectedMethod == "Cash",
            onClick = {
                selectedMethod = "Cash"
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        PaymentMethod(
            name = "Card",
            selected = selectedMethod == "Card",
            onClick = {
                selectedMethod = "Card"
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        PaymentMethod(
            name = "SurgeX Wallet",
            selected = selectedMethod == "Wallet",
            onClick = {
                selectedMethod = "Wallet"
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onPaymentSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SurgeWhite,
                contentColor = SurgeBlack
            )
        ) {

            Text(
                text = "CONFIRM PAYMENT",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }

        TextButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "CANCEL",
                color = SurgeGrey,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun PaymentMethod(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        color = if (selected)
            SurgeWhite
        else
            SurgeSurface,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {

        Row(
            modifier = Modifier.padding(horizontal = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = name,
                modifier = Modifier.weight(1f),
                color = if (selected)
                    SurgeBlack
                else
                    SurgeWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = if (selected) "✓" else "○",
                color = if (selected)
                    SurgeBlack
                else
                    SurgeGrey,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
