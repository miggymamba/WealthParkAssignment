package  com.example.wealthparkassignment.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.wealthparkassignment.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogUtil {
    lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    var alertDialog: AlertDialog? = null
    var materialAlertDialog: AlertDialog? = null

    companion object {
        private var dialogUtil: DialogUtil? = null
        val instance: DialogUtil
            get() {
                if (dialogUtil == null) {
                    dialogUtil = DialogUtil()
                }
                return dialogUtil!!
            }
    }

    fun showLoadingAlertDialog(context: Context) {
        if (alertDialog != null && alertDialog?.isShowing!!) return

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
        materialAlertDialogBuilder.setView(R.layout.dialog_loading)
        materialAlertDialogBuilder.setCancelable(false)
        alertDialog = materialAlertDialogBuilder.create()
        alertDialog!!.show()
    }

    fun dismissLoadingAlertDialog() {
        alertDialog?.dismiss()
        alertDialog = null
    }

    fun dismissAlertDialog() {
        materialAlertDialog?.dismiss()
        materialAlertDialog = null

    }

    fun showAlertDialogMessageOnly(context: Context, title: String) {
        if (materialAlertDialog != null && materialAlertDialog?.isShowing!!) return
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
        materialAlertDialogBuilder.setMessage(title)
        materialAlertDialogBuilder.setPositiveButton("DISMISS", null)
        materialAlertDialogBuilder.setCancelable(false)

        materialAlertDialog = materialAlertDialogBuilder.create()

        materialAlertDialog!!.show()
    }

}
