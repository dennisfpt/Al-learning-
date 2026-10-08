# Learning GPS AI (đồ án BTEC Unit 22)

Ứng dụng Android (Kotlin + Jetpack Compose): AI học mọi môn, mọi trình độ, có lộ trình cá nhân hóa và chụp ảnh quét bài tập.
Màu chủ đạo **đỏ – trắng**. Xem thiết kế tại `design/learning-gps-ai-preview.html` (mở bằng trình duyệt) và `docs/DESIGN.md`.

## Chạy app
1. `git clone` repo, mở bằng Android Studio (template Empty Activity, package `com.example.alstudymentor`).
2. Đợi Gradle Sync, cắm điện thoại (bật Gỡ lỗi USB) rồi bấm Run.
3. `MainActivity.kt` chỉ cần: `setContent { AiStudyMentorApp() }`.

## Code hiện có (`app/src/main/java/com/example/alstudymentor/`)
- `AiStudyMentorUi.kt`: theme, điều hướng, các màn Chào mừng, Đăng ký, Thiết lập, Trang chủ, Hỏi đáp, Lời giải, Chat, Quiz, Thư viện, Tiến độ.
- `LearningRoadmapUi.kt`: Chọn môn, AI phân tích, Lộ trình, Kế hoạch tuần, Bài học, Kết quả.
- Code chưa được build thử, nếu có lỗi nhỏ thì sửa theo thông báo của Android Studio.

## Việc cần làm (đối chiếu với `design/` )
- [ ] Màn **Đăng nhập** (thêm vào enum `Screen` và khối `when`).
- [ ] Màn **Chụp bài** (khung camera, vạch quét, nút chụp, lớp "Đang nhận diện") và **Kết quả quét**.
- [ ] Màn **Thiết lập** đổi thành 5 cấp: Mầm non & Tiểu học, THCS, THPT, Đại học & Cao đẳng, Chuyên môn & Người đi làm.
- [ ] Rà lại từng màn so với bản xem trước (màu, bo góc, khoảng cách).
- [ ] Sau cùng: nối camera thật (CameraX + ML Kit) và AI qua API.

## Làm việc nhóm
- Mỗi người một nhánh: `git checkout -b ten-nhanh`, push rồi tạo Pull Request vào `main`.
- Mỗi người một file màn hình riêng để tránh conflict. Luôn `git pull` trước khi bắt đầu.
