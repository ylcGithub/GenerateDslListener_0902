package action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.editor.Editor
import util.InsertCodeHelper
import util.VelocityEngineHelper
import view.DslListenerBuilderDialog
import view.showDialog

class GenerateDslListener : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        if (event.project == null) {
            return
        }

        VelocityEngineHelper.initEngine()
        val builderDialog = DslListenerBuilderDialog()
        builderDialog.setDialogListener(object : DslListenerBuilderDialog.DialogListener {
            override fun onOkBtnClicked(map: MutableMap<String, String>?) {
                if (map == null || map.isEmpty()) {
                    return
                }

                generateCode(map, event)
            }

            override fun onCancelBtnClicked() {

            }
        })

        builderDialog.showDialog(height = 600)
    }

    override fun update(event: AnActionEvent) {
        if (event.project == null) {
            event.presentation.isEnabled = false
        }

        val editor: Editor? = event.dataContext.getData(LangDataKeys.EDITOR)
        if (editor == null) {
            event.presentation.isEnabled = false
        }

        event.presentation.isEnabled = true
    }

    private fun generateCode(map: MutableMap<String, String>, event: AnActionEvent) {
        val generateCode: String = VelocityEngineHelper.evaluate(map)
        val editor: Editor? = event.dataContext.getData(LangDataKeys.EDITOR)
        if (editor != null) {
            try {
                InsertCodeHelper.doInsert(generateCode, editor)
                println("generated code is succeed, generated code is :\n $generateCode")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}