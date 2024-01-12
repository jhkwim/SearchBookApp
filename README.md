# 도서검색앱

MainActivity에 SearchFragment, DetailFragment로 구성되어 있습니다.

검색 결과는 RecyclerView로 구현 했습니다.

UI Layer와 Data Layer로 구현했습니다.

검색어 or, not operator 구현은 BookRepository 내부에 구현했습니다.

## 모듈 구성
1. app
2. core
   1. data
   2. database
   3. model
   4. network
3. feature
   1. search
   2. detail
