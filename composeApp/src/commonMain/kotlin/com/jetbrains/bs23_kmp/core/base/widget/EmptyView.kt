package com.jetbrains.bs23_kmp.core.base.widget

//import com.airbnb.lottie.compose.LottieAnimation
//import com.airbnb.lottie.compose.LottieCompositionSpec
//import com.airbnb.lottie.compose.LottieConstants
//import com.airbnb.lottie.compose.animateLottieCompositionAsState
//import com.airbnb.lottie.compose.rememberLottieComposition
//import com.bs23.msfa.R
//import com.bs23.msfa.core.base.component.SmallSpacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetbrains.bs23_kmp.core.base.component.SmallSpacer
import kmp_app_template.composeapp.generated.resources.Res
import kmp_app_template.composeapp.generated.resources.no_data_available
import org.jetbrains.compose.resources.stringResource


@Composable
fun EmptyView(
    modifier: Modifier = Modifier,
    message: String = stringResource(resource = Res.string.no_data_available),
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .wrapContentHeight(Alignment.CenterVertically)
    ) {

        Box(modifier = modifier.size(256.dp), contentAlignment = Alignment.Center) {
//            NoDataFound()
        }
        SmallSpacer()


        androidx.compose.material3.Text(
            text = message,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            textAlign = TextAlign.Center
        )
    }
//    Column(
//        modifier = modifier
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Icon(
//            painter = rememberVectorPainter(Icons.Default.Home),
//            contentDescription = null,
//            tint = Red,
//            modifier = modifier
//        )
//        Text(
//            text = stringResource(id = R.string.text_no_data_found),
//            style = MaterialTheme.typography.headlineMedium,
//            textAlign = TextAlign.Center,
//            modifier = modifier
//                .fillMaxWidth()
//        )
//    }
}

//Lottie Animation We will implement it later

//@Composable
//fun NoDataFound(){
//        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.not_found_3))
//        val animationState by animateLottieCompositionAsState(
//            composition = composition,
//            isPlaying = true,
//        )
//
//        LottieAnimation(composition = composition, progress = {animationState})
//
//}
//@Preview(showBackground = true, name = "Light Mode")
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
//@Composable
//fun EmptyPageViewPreview() {
//    AppTheme {
//        EmptyView()
//    }
//}