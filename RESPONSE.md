專案架構使用 MVVM
# 分為 Data Layer 跟 UI Layer
# Data Layer
  - 網路需求使用 Retrofit 實作, NetworkManager來管理, 並用 singleton 模式, 希望只有一份物件減少資源浪費.
  - 使用 Gson 來解析撈回的資料.
  - UserRepository 做為統一網路取得資料的介面, 包括處理網路是否成功.
  - 使用 retrofit 的 enqueue 來實作非同步執行

# UI Layer
  - UsersViewModel 接收 UserRepository回來的資料, 再看是否還要處理顯示邏輯的部分, 並使用 LiveData 把資料送到訂閱者 (UsersActivity).
  - UsersActivity 直接是 app Launcher的頁面.
  - 使用 RecyclerView 來做 User List 頁面.
  - 從 Livedata 拿到資料後, 傳給 UserListAdapter 更新資料顯示.
  - 用 Glide 套件來顯示圖片.

# Git
  - 有 master 跟 develop 兩條 branch.