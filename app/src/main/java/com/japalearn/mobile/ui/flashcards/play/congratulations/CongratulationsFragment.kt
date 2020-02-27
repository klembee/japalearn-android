package com.japalearn.mobile.ui.flashcards.play.congratulations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.japalearn.mobile.R
import com.japalearn.mobile.data.models.learning.Vocab

class CongratulationsFragment(
    val gotRight: Set<Vocab>,
    val gotWrong: Set<Vocab>
): Fragment() {

    private lateinit var okButton: Button
    private lateinit var nbGotRightTxt: TextView
    private lateinit var ratioRightWrongTxt: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_congratulations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nbGotRightTxt = view.findViewById(R.id.item_got_right_txt)
        ratioRightWrongTxt = view.findViewById(R.id.ratio_right_wrong_txt)
        okButton = view.findViewById(R.id.congrats_ok_button)

        nbGotRightTxt.text = resources.getQuantityString(R.plurals.items_got_right, gotRight.size, gotRight.size)
        if(gotWrong.size != 0) {
            ratioRightWrongTxt.text = getString(
                R.string.ratio_right_wrong_txt,
                ((gotRight.size.toDouble() / (gotWrong.size + gotRight.size).toDouble()) * 100).toInt()
            )
        }else{
            ratioRightWrongTxt.text = getString(R.string.ratio_right_wrong_txt,100)
        }

        okButton.setOnClickListener {
            activity?.finish()
        }
    }
}