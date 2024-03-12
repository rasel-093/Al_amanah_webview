import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(value: Int) {
    var progress = value.toFloat() / 100
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(15.dp)
            .clip(RoundedCornerShape(8.dp)),
        progress = progress,
        color = Color(0xFF29b4de),
        trackColor = Color(0xFFFFFFFF)
    )
}
