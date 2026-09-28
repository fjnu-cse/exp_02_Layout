package com.example.android_ex2_layout

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorController(
    private val activity: AppCompatActivity
) {
    private lateinit var display: TextView

    private var currentInput = "0"
    private var storedValue: BigDecimal? = null
    private var pendingOperator: Char? = null
    private var waitingForOperand = false

    fun connect() {
        display = activity.findViewById(R.id.calculator_display)
        display.text = "0.0"

        val digitKeys = listOf(
            R.id.key_0 to "0",
            R.id.key_1 to "1",
            R.id.key_2 to "2",
            R.id.key_3 to "3",
            R.id.key_4 to "4",
            R.id.key_5 to "5",
            R.id.key_6 to "6",
            R.id.key_7 to "7",
            R.id.key_8 to "8",
            R.id.key_9 to "9"
        )

        digitKeys.forEach { (viewId, digit) ->
            activity.findViewById<TextView>(viewId).setOnClickListener {
                appendDigit(digit)
            }
        }

        activity.findViewById<TextView>(R.id.key_dot).setOnClickListener {
            appendDecimalPoint()
        }

        activity.findViewById<TextView>(R.id.key_plus).setOnClickListener {
            chooseOperator('+')
        }

        activity.findViewById<TextView>(R.id.key_minus).setOnClickListener {
            chooseOperator('-')
        }

        activity.findViewById<TextView>(R.id.key_times).setOnClickListener {
            chooseOperator('*')
        }

        activity.findViewById<TextView>(R.id.key_divide).setOnClickListener {
            chooseOperator('/')
        }

        activity.findViewById<TextView>(R.id.key_equals).setOnClickListener {
            calculateResult()
        }
    }

    private fun appendDigit(digit: String) {
        if (waitingForOperand) {
            currentInput = ""
            waitingForOperand = false
        }

        if (currentInput.length >= 15) return

        currentInput = when {
            currentInput.isEmpty() -> digit
            currentInput == "0" && digit != "0" -> digit
            currentInput == "0" -> "0"
            else -> currentInput + digit
        }

        display.text = currentInput
    }

    private fun appendDecimalPoint() {
        if (waitingForOperand) {
            currentInput = "0"
            waitingForOperand = false
        }

        if (!currentInput.contains(".")) {
            currentInput += "."
        }

        display.text = currentInput
    }

    private fun chooseOperator(operator: Char) {
        val value = currentInput.toBigDecimalOrNull() ?: BigDecimal.ZERO
        val previousOperator = pendingOperator
        val previousValue = storedValue

        if (previousOperator != null &&
            previousValue != null &&
            !waitingForOperand
        ) {
            val result = calculate(previousValue, value, previousOperator)

            if (result == null) {
                showError()
                return
            }

            storedValue = result
            currentInput = format(result)
            display.text = currentInput
        } else if (previousValue == null) {
            storedValue = value
        }

        // 连续按运算符时，替换待执行的运算符。
        pendingOperator = operator
        waitingForOperand = true
    }

    private fun calculateResult() {
        val left = storedValue ?: return
        val operator = pendingOperator ?: return

        // 运算符后没有输入第二个数字时，不执行计算。
        if (waitingForOperand) return

        val right = currentInput.toBigDecimalOrNull() ?: return
        val result = calculate(left, right, operator)

        if (result == null) {
            showError()
            return
        }

        currentInput = format(result)
        display.text = currentInput
        storedValue = null
        pendingOperator = null
        waitingForOperand = true
    }

    private fun calculate(
        left: BigDecimal,
        right: BigDecimal,
        operator: Char
    ): BigDecimal? {
        return when (operator) {
            '+' -> left.add(right)
            '-' -> left.subtract(right)
            '*' -> left.multiply(right)
            '/' -> {
                if (right.compareTo(BigDecimal.ZERO) == 0) {
                    null
                } else {
                    left.divide(right, 10, RoundingMode.HALF_UP)
                }
            }
            else -> null
        }
    }

    private fun format(value: BigDecimal): String {
        return value.stripTrailingZeros().toPlainString()
    }

    private fun showError() {
        display.text = "Error"
        currentInput = "0"
        storedValue = null
        pendingOperator = null
        waitingForOperand = true
    }
}