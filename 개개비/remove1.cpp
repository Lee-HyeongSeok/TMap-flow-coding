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

	// cend() : 옛날의 끝을 반환
	cout << "pre:";
	copy(coll.cbegin(), coll.cend(), ostream_iterator<int>(cout, " "));
	cout << endl;

	// remove()는 자신이 넘겨받은 모음의 요소 수를 바꾸지 않는다.
	// 3의 값을 갖는 요소들은 그 다음 요소 값으로 덮어써진다.
	// 모음의 끝에가면 덮어써지지 않은 오래된 요소들이 여전히 남음, 논리적으로 이 요소들은
	// 모음에 속하지 않는다.
	remove(coll.begin(), coll.end(), 3);

	cout << "post: ";
	copy(coll.cbegin(), coll.cend(), ostream_iterator<int>(cout, " "));
	cout << endl;
}