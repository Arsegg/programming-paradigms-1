�������� ������� 6. ������ ���������
---
 * *�������*
    * ������������� ����������� �������� ��������:
        * `mod` � ������ �� ������, ��������� ��� � ��������� (`1 + 5 mod 3` ����� `1 + (5 mod 3)` ����� `3`);
        * `<<` � ����� �����, ����������� ��������� (`1 << 5 + 3` ����� `1 << (5 + 3)` ����� 256);
        * `>>` � ����� ������, ����������� ��������� (`1024 >> 5 + 3` ����� `1024 >> (5 + 3)` ����� 4);
    * [�������� ��� ������](java/expression/ParserEasyTest.java)
 * *�����������*
    * ����������� �������� �� �������� ��������.
    * ������������� ����������� ������� �������� (��������� ��� � �������� ������):
        * `abs` � ������ �����, `abs -5` ����� 5;
        * `square` � ���������� � �������, `square -5` ����� 25.
    * [�������� ��� ������](java/expression/ParserHardTest.java)
 * *�������*
    * ����������� ��������� [Parser](java/expression/Parser.java)
    * [�������� ��� ������](java/expression/ParserTest.java)