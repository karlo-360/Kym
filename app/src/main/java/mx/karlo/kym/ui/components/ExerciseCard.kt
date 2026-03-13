package mx.karlo.kym.ui.components
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import mx.karlo.kym.data.local.routine.RoutineExercise

@Composable
fun ExerciseCard(
    exercise: RoutineExercise,
    isReorderable: Boolean = false,
    dragState: Boolean = false,
    reorderIcon: @Composable () -> Unit,
    counter: @Composable () -> Unit,
) {

    val interactionSource = remember { MutableInteractionSource() }

    val elevation by animateDpAsState(
        targetValue = if (dragState) 12.dp else 1.dp,
    )

    val modifier = if (isReorderable) {
        Modifier
            .clip(CardDefaults.shape)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = {}
            )
    } else {
        Modifier
    }

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = elevation
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(top = 8.dp)
                    .align(Alignment.TopCenter),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    buildAnnotatedString {
                        append(exercise.exerciseName)
                        if (exercise.isUnilateral) {
                            withStyle(
                                SpanStyle(
                                    color = Color.Gray
                                )
                            ) {
                                append(" (Unilateral)")
                            }
                        }
                    },
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Box(
                modifier = Modifier
                    .defaultMinSize(minHeight = 120.dp)
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 48.dp,
                    )
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                counter()
            }

            Column(modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterEnd)
            ) {
                AnimatedVisibility(
                    visible = isReorderable,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    reorderIcon()
                }
            }

        }
    }
}