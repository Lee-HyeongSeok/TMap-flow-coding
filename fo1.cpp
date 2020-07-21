#include <deque>
#include <algorithm>
#include <functional>
#include <iostream>
#include "D:\C++ stl\Algorithm_factor_Series\print.hpp"

using namespace std;

int main() {
	deque<int> coll = { 1, 2, 3, 5, 7, 11, 13, 17, 19 };
	PRINT_ELEMENTS(coll, "initialized : ");

	// cbegin()~cend() : ����
	// begin() : ���
	// coll ���� �ִ� ��� ���� ��ȣ�� ������
	// negate<int> : int���� ��Ұ��� ��ȣ�� �ڹٲ� ��ȯ�ϴ� ������ Ŭ���� ���ø��� �Լ���ü
	// transform() : �˰���, ù ��° ������ ������ �� ��° ������ ������
	transform(coll.cbegin(), coll.cend(), coll.begin(), negate<int>());
	PRINT_ELEMENTS(coll, "negated : ");

	// cbegin()~cend() : ù ��° ����
	// cbegin() : �� ��° ����
	// begin() : ���
	// coll ���� ��� ���� �����Ѵ�. (multiplies�� ����)
	// �� ��ҿ� �ڽ��� ���� ������ �� �� ������ ���������.
	transform(coll.cbegin(), coll.cend(), coll.cbegin(), coll.begin(), multiplies<int>());
	PRINT_ELEMENTS(coll, "squared : ");
}