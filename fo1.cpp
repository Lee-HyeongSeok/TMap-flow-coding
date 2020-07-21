#include <deque>
#include <algorithm>
#include <functional>
#include <iostream>
#include "D:\C++ stl\Algorithm_factor_Series\print.hpp"

using namespace std;

int main() {
	deque<int> coll = { 1, 2, 3, 5, 7, 11, 13, 17, 19 };
	PRINT_ELEMENTS(coll, "initialized : ");

	// cbegin()~cend() : 원본
	// begin() : 대상
	// coll 내에 있는 모든 값의 부호를 뒤집음
	// negate<int> : int형의 요소값의 부호를 뒤바꿔 반환하는 선정의 클래스 템플릿인 함수객체
	// transform() : 알고리즘, 첫 번째 변형된 모음을 두 번째 모음에 삽입함
	transform(coll.cbegin(), coll.cend(), coll.begin(), negate<int>());
	PRINT_ELEMENTS(coll, "negated : ");

	// cbegin()~cend() : 첫 번째 원본
	// cbegin() : 두 번째 원본
	// begin() : 대상
	// coll 내의 모든 값을 제곱한다. (multiplies를 통해)
	// 각 요소에 자신의 값이 곱해진 후 새 값으로 덮어써진다.
	transform(coll.cbegin(), coll.cend(), coll.cbegin(), coll.begin(), multiplies<int>());
	PRINT_ELEMENTS(coll, "squared : ");
}