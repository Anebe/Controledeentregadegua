package com.gabriel_barros.controle_entregua_agua.ui.components.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker (label: String = "", onChoose: (LocalDate) -> Unit){
    var calendarShow = rememberUseCaseState(visible = false)

    Button(
        onClick = { calendarShow.show() }) {
        Row (horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentWidth()){
            Text(label)
            Spacer(modifier = Modifier.width(9.dp))
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Escolher Data")
        }
    }
    CalendarDialog(state = calendarShow,
        selection = CalendarSelection.Date(
            selectedDate = LocalDate.now(),
            onSelectDate = { onChoose(it) }
        ))
}