Домашнее задание 2. Бинарный поиск
----
Модификации
 * *Простая*
    * Если в массиве `a` отсутствует элемент, равный `x`, то требуется
      вывести индекс вставки в формате, определенном в
      [`Arrays.binarySearch`](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#binarySearch-int:A-int-).
    * Класс должен иметь имя `BinarySearchMissing`
    * [Исходный код тестов](java/BinarySearchMissingTest.java)
    * [Откомпилированные тесты](artifacts/BinarySearchMissingTest.jar)
 * *Усложненная*
    * Требуется вывести два числа: начало и длину диапазона элементов,
      равных `x`. Если таких элементов нет, то следует вывести
      пустой диапазон, у которого левая граница совпадает с местом
      вставки элемента `x`.
    * Не допускается использование типов `long` и `BigInteger`.
    * Класс должен иметь имя `BinarySearchSpan`
    * [Исходный код тестов](java/BinarySearchSpanTest.java)
    * [Откомпилированные тесты](artifacts/BinarySearchSpanTest.jar)
 * *Базовая*
    * [Исходный код тестов](java/BinarySearchTest.java)
    * [Откомпилированные тесты](artifacts/BinarySearchTest.jar)