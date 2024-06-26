# CSVParser

## Описание

Тестовое задание: Необходимо отсортировать csv-файл (размер файла на порядки превышает объем доступной оперативной памяти) по первому полю, можно считать, что оно целочисленное. Первое поле (ключ) может иметь не уникальное значение. Длины каждой из строк могут отличаться.

Необходимо реализовать собственный метод, без использования сторонних библиотек и базы данных.

## Требования

- Реализация должна учитывать, что размер файла значительно превышает объем доступной оперативной памяти.
- Сортировка должна выполняться по первому полю, которое является целочисленным.
- Значения первого поля могут быть не уникальными.
- Длины строк могут отличаться.

## Описание решения

1. **Чтение файла:** Файл читается по частям, чтобы избежать превышения объема оперативной памяти.
2. **Внешняя сортировка:** Для сортировки большого файла используется алгоритм внешней сортировки, который включает следующие этапы:
   - **Разбиение:** Исходный файл разбивается на небольшие отсортированные части.
   - **Слияние:** Отсортированные части сливаются в один отсортированный файл.
3. **Использование временных файлов:** Во время сортировки и слияния создаются временные файлы для хранения промежуточных результатов.
