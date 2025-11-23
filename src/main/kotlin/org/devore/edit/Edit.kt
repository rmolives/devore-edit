package org.devore.edit

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neoutils.highlight.compose.remember.rememberHighlight
import com.neoutils.highlight.compose.remember.rememberTextFieldValue
import com.neoutils.highlight.core.extension.textColor
import com.neoutils.highlight.core.util.UiColor

@Composable
fun TextEditor() {
    val highlight = rememberHighlight {
        textColor {
            "(.)"
                .toRegex()
                .fully(UiColor.Rgb(0, 0, 139))
        }
        textColor {
            "(([0-9]+\\.?[0-9]*)|(\\.[0-9]+))"
                .toRegex()
                .fully(UiColor.Rgb(0, 128, 0))
        }
        textColor {
            "([()\\[\\]])"
                .toRegex()
                .fully(UiColor.Red)
        }
        textColor {
            "(\"(?:\\\\.|[^\\\\\"])*\")"
                .toRegex()
                .fully(UiColor.Rgb(0, 255, 0))
        }
    }
    val textFieldValue = rememberSaveable { mutableStateOf(TextFieldValue()) }
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        BasicTextField(
            value = highlight.rememberTextFieldValue(
                textFieldValue.value,
            ).copy(
                composition = null,
            ),
            onValueChange = { textFieldValue.value = it },
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .verticalScroll(scrollState)
                .padding(12.dp),
            textStyle = TextStyle(
                fontSize = 24.sp,
                lineHeight = 28.sp
            ),
            maxLines = Int.MAX_VALUE
        )

        // 滚动条指示器
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState)
        )
    }
}
