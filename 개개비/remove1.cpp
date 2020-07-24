#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <list>
#include <iterator>

using namespace std;

template <typename T>
inline void PRINT_ELEMENTS(const T& coll, const std::string& optstr = "") {
	cout << optstr << ' ';
	for (const auto& elem : coll) {
		cout << elem << ' ';
	}
	cout << endl;
}
int main() {
	list<int> coll;

	for (int i = 1; i <= 6; i++) {
		coll.push_front(i);
		coll.push_back(i);
	}

	// cend() : ������ ���� ��ȯ
	cout << "pre:";
	copy(coll.cbegin(), coll.cend(), ostream_iterator<int>(cout, " "));
	cout << endl;

	// remove()�� �ڽ��� �Ѱܹ��� ������ ��� ���� �ٲ��� �ʴ´�.
	// 3�� ���� ���� ��ҵ��� �� ���� ��� ������ ���������.
	// ������ �������� ��������� ���� ������ ��ҵ��� ������ ����, �������� �� ��ҵ���
	// ������ ������ �ʴ´�.
	remove(coll.begin(), coll.end(), 3);

	cout << "post: ";
	copy(coll.cbegin(), coll.cend(), ostream_iterator<int>(cout, " "));
	cout << endl;
}