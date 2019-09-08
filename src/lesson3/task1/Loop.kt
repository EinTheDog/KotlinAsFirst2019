@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int{
    var l=0
    var k=n
    if (n==0){
        l=1
    }else{
        while (abs(k)>0){
            k=k/10
            l++
        }
    }

    return l
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int= if (n<3) 1 else fib (n-1)+fib(n-2)

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int{
    val max:Int
    val min:Int
    var k=0

    if (m>n){
        max=m
        min=n
    }else{
        max=n
        min=m
    }


    for (i in max..max*min step max){
        if (k!=0) break
        for (j in min..max*min step min){
            if (j>i) break
            if (i==j){
                k=i
                break
            }
        }
    }

    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var k=2
    if (n%k==0){
        return k
    }else{
        k++
        while (n%k!=0){
            k+=2
        }
        return k
    }

}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int{
    var k=n-1
    while (n%k!=0){
        k--
    }
    return k
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean  {
    val min:Int
    var p=true
    if (m%2==0 && n%2==0){
        p=false
    }else{
        if (m<n) min=m else min=n
        for (i in 3..min step 2){
            if (m%i==0 && n%i==0) p=false
        }
    }

    return p
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean{
    var ans=false
    for (i in sqrt(m.toDouble()).toInt()..sqrt(n.toDouble()).toInt()+1){
        if (i*i>=m && i*i<=n) ans=true
    }
    return ans
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var y=x
    var k=0
    while (y>1){
        if (y%2==0) y=y/2 else y=y*3+1
        k++
    }
    return k
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double{
    var x1=x

    while (x1>=2*PI){
        x1-=2*PI
    }

    fun fact(n:Int):Double{
        var a=1.0
        for (i in 2..n){
            a*=i
        }
        return a
    }

    var ans=0.0
    var k=1
    var next:Double

    next=(-1.0).pow(k-1)*x1.pow(2*k-1)/fact(2*k-1)
    while(abs(next)>eps){
        ans+=next
        k++
        next=(-1.0).pow(k-1)*x1.pow(2*k-1)/fact(2*k-1)
    }
    return  ans
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double{
    var x1=x

    while (x1>=2*PI){
        x1-=2*PI
    }

    fun fact(n:Int):Double{
        var a=1.0
        for (i in 2..n){
            a*=i
        }
        return a
    }

    var ans=0.0
    var k=0
    var next:Double

    next=(-1.0).pow(k)*x1.pow(2*k)/fact(2*k)
    while(abs(next)>eps){
        ans+=next
        k++
        next=(-1.0).pow(k)*x1.pow(2*k)/fact(2*k)
    }
    return  ans
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int{
    var n1=n
    var m=0
    while (n1>0){
        m=m*10+n1%10
        n1=n1/10
    }
    return m
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var n1=n
    var l=1
    var ans=true

    while (n1>10){
        l=l*10
        n1=n1/10
    }

    n1=n
    while (n1>10){
        if (n1/l!=n1%10){
            ans=false
            break
        }
        n1=n1%l
        l=l/100
        n1=n1/10
    }

    return ans
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean{
    var n1=n
    var ans=false
    var l=1
    val p:Int

    while (n1>10){
        l=l*10
        n1=n1/10
    }

    p=n/l
    n1=n
    while (n1>10){
        if (p!=n1%10){
            ans=true
            break
        }
        n1=n1/10
    }

    return ans
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var m=0
    var ans=0
    var k=1
    var d=1
    var l:Int
    var y:Int

    fun Len(x:Int):Int{
        var a=0
        var x1=x
        while (x1>0){
            x1=x1/10
            a++
        }
        return a
    }

    while (m<n){
        y=k*k
        l=Len(y)
        m+=l
        if (m>=n){
            for (i in 1..m-n){
                d=d*10
            }
            y=y/d
            ans=y%10
        }

        k++
    }

    return ans
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int{
    var m=0
    var ans=0
    var k=1
    var d=1
    var l:Int
    var y:Int

    fun Len(x:Int):Int{
        var a=0
        var x1=x
        while (x1>0){
            x1=x1/10
            a++
        }
        return a
    }

    fun fibo(n: Int): Int= if (n<3) 1 else fibo(n-1)+fibo(n-2)

    while (m<n){
        y=fibo(k)
        l=Len(y)
        m+=l
        if (m>=n){
            for (i in 1..m-n){
                d=d*10
            }
            y=y/d
            ans=y%10
        }

        k++
    }

    return ans
}
