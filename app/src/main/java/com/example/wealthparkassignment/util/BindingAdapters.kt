package  com.example.wealthparkassignment.util


import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.webkit.WebView
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.wealthparkassignment.util.extensions.gone
import com.example.wealthparkassignment.util.extensions.visible
import com.google.android.material.textfield.TextInputLayout
import timber.log.Timber
import java.text.DecimalFormat


class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("app:tilErrorText")
        fun setErrorMessage(view: TextInputLayout, errorMessage: String?) {
            view.error = errorMessage
            if (errorMessage != null) view.requestFocus()
        }

        @JvmStatic
        @BindingAdapter("app:etErrorText")
        fun setErrorMessageEditText(view: EditText, errorMessage: String?) {
            view.error = errorMessage
            if (errorMessage != null) view.requestFocus()
        }

        @JvmStatic
        @BindingAdapter("app:htmlToText")
        fun setHtmlText(view: TextView, htmlText: String?) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                view.text = Html.fromHtml(htmlText ?: "", Html.FROM_HTML_MODE_COMPACT)
            else {
                view.text = Html.fromHtml(htmlText ?: "")
            }
        }

        @JvmStatic
        @BindingAdapter("app:imageUri")
        fun loadImageWithUrl(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context).load(imageUrl).centerCrop().into(imageView)
        }

        @JvmStatic
        @BindingAdapter("app:imageResource")
        fun loadImageWithId(imageView: ImageView, imageResourceId: Int) {
            Glide.with(imageView.context).load(imageResourceId).into(imageView)
        }

        @JvmStatic
        @BindingAdapter("app:imageUrlCircle")
        fun loadImageWithUrlAndCircleCrop(imageView: ImageView, imageUrl: String?) {
            //Glide.with(imageView.context).load(if (imageUrl.isNullOrEmpty()) R.drawable.patient else imageUrl).circleCrop().into(imageView)
        }


        @JvmStatic
        @BindingAdapter("app:textWithState")
        fun setTextState(view: TextView, text: String?) {
            view.text = text

            if (text.equals("open", true))
                view.setTextColor(Color.GREEN)
            else
                view.setTextColor(Color.RED)
        }

        @JvmStatic
        @BindingAdapter("app:tilStartIconDrawable")
        fun addIcon(view: TextInputLayout, drawable: Drawable?) {
            view.startIconDrawable = drawable
        }

        @JvmStatic
        @BindingAdapter(value = ["beVisible"], requireAll = false)
        fun View.beVisible(condition: Boolean) {
            Timber.d("beVisible $condition")
            if (condition) this.visible() else this.gone()
        }

        @JvmStatic
        @BindingAdapter("app:partneredWith")
        fun partneredWith(view: TextView, text: String) {
            val spannablecontent = SpannableString("Partnered with $text")
            spannablecontent.setSpan(
                ForegroundColorSpan(
                    Color.argb(255, 255, 196, 29)
                ), spannablecontent.indexOf(text), spannablecontent.length, 0
            )
            view.text = spannablecontent
        }

/*        @JvmStatic
        @BindingAdapter(value = ["layoutPaddings"], requireAll = false)
        fun View.setLayoutPadding(paddings: Paddings) {
            this.setPadding(
                getDpValue(context, paddings.left),
                getDpValue(context, paddings.top),
                getDpValue(context, paddings.right),
                getDpValue(context, paddings.bottom)
            )
        }*/

        @JvmStatic
        @BindingAdapter("app:setBackgroundColorWithId")
        fun setBackgroundColorWithId(view: View, color: Int) {
            view.setBackgroundColor(ContextCompat.getColor(view.context, color))
        }


        @JvmStatic
        @BindingAdapter("app:isBold")
        fun setBold(view: TextView, isBold: Boolean) {
            if (isBold) {
                view.setTypeface(null, Typeface.BOLD)
            } else {
                view.setTypeface(null, Typeface.NORMAL)
            }
        }


        @JvmStatic
        @BindingAdapter("app:formatAmount")
        fun formatAmount(view: TextView, amount: Double) {
            view.text = DecimalFormat("#,###.##").format(amount)
        }

        @JvmStatic
        @BindingAdapter("app:setTextAutoCompleteTextView")
        fun setTextWithoutFiltering(view: AutoCompleteTextView, text: String?) {
            view.setText(text, false)
        }

        @JvmStatic
        @BindingAdapter("app:webViewUrl")
        fun loadUrl(webView: WebView, url: String) {
            webView.loadUrl(url)
        }

        @JvmStatic
        @BindingAdapter("app:imageDrawableAsGif")
        fun loadDrawableGifImage(imageView: ImageView, drawable: Drawable){
            Glide.with(imageView.context).asGif().load(drawable).circleCrop().into(imageView)
        }

        @JvmStatic
        @BindingAdapter("app:imageResourceIdAsGif")
        fun loadResourceIdGifImage(imageView: ImageView, @DrawableRes drawableResourceId: Int){
            Glide.with(imageView.context).asGif().load(drawableResourceId).circleCrop().into(imageView)
        }

        /*@JvmStatic
        @BindingAdapter("app:imageUri")
        fun loadImageWithUrl(imageView: ImageView, imageUrl: String){
            Glide.with(imageView.context).load(imageUrl).into(imageView)
        }*/
    }
}