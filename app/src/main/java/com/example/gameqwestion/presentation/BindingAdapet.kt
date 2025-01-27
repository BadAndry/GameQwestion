package com.example.gameqwestion.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.gameqwestion.R
import com.example.gameqwestion.domain.entity.GameResult

@BindingAdapter("requireAnswers")
fun bindingAdapter(textView: TextView, count: Int) {
   textView.text = String.format(
      textView.context.getString(R.string.required_score),
      count.toString(),
      )
}
@BindingAdapter("requireScore")
fun bindingSocre(textView: TextView, score: Int){
   textView.text = String.format(
      textView.context.getString(R.string.score_answers),
         score.toString())
}

@BindingAdapter("requirePercent")
fun bindingPercenteg(textView: TextView, percent: Int ){
  textView.text = String.format(textView.context.getString(R.string.required_percentage),
      percent.toString())
}

@BindingAdapter("requireScorePercent")
fun bindingScorePercent(textView: TextView, gameResult: GameResult){
   textView.text = String.format(textView.context.getString(R.string.score_percentage),
      getPercentOfRightAnswer(gameResult).toString())
}

@BindingAdapter("requireEmoje")
fun bindingEmoje (imageView: ImageView, gameResult: GameResult){
   imageView.setImageResource(emojeResult(gameResult))
}

private fun emojeResult(gameResult: GameResult): Int {
   if (gameResult.winner) {
      return R.drawable.ic_smile
   }  else {
      return   R.drawable.ic_sad
   }
}
private fun getPercentOfRightAnswer(gameResult: GameResult) = with(gameResult){
   if (countOfRightAnswers == 0) {
      0
   } else {
      ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
   }
}
@BindingAdapter("enoughCountRightAnswer")
fun bindingCountEnoughRightAnswer(textView: TextView, enough: Boolean) {
   textView.setTextColor(getRightColorPrBar(textView.context, enough))
}

@BindingAdapter("enoughPercent")
fun enoughPercent(progressBar: ProgressBar, enoughPercent: Boolean) {
   val color = getRightColorPrBar(progressBar.context, enoughPercent)
  progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getRightColorPrBar(context: Context, right: Boolean): Int  {
   val colorResId = if (right) {
      android.R.color.holo_green_light
   } else
      android.R.color.holo_red_dark
   return ContextCompat.getColor((context), colorResId)
}
@BindingAdapter("numberAsText")
fun bingingNumberAsText(textView: TextView, number: Int){
textView.text = number.toString()
}

interface onClickTextOption {
   fun onClick(option: Int)
}
@BindingAdapter("textViewClicl")
fun clickListener(textView: TextView, answer: onClickTextOption) {
   textView.setOnClickListener {
      answer.onClick(textView.text.toString().toInt())
   }
}
