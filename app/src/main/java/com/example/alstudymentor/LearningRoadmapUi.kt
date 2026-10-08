package com.example.alstudymentor

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- Shared pieces ----------
@Composable
fun RoadStep(n: Int, title: String, sub: String, state: Int, onClick: () -> Unit = {}) = // 0 chưa học, 1 đang học, 2 xong
    SoftCard(Modifier.clickable { onClick() }) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(Modifier.size(34.dp).clip(CircleShape).background(if (state == 0) BrandLight else Brand), contentAlignment = Alignment.Center) {
                Text(if (state == 2) "✓" else "$n", color = if (state == 0) Brand else Color.White, fontWeight = FontWeight.Bold)
            }
            Column(Modifier.weight(1f)) { Text(title, fontWeight = FontWeight.SemiBold); Text(sub, color = Gray, fontSize = 12.sp) }
            Text(if (state == 2) "✅" else "›", color = Brand, fontSize = 18.sp)
        }
    }

@Composable
fun DayRow(day: String, task: String, state: Int, onClick: () -> Unit = {}) = // 2 xong, 1 hôm nay, 0 sắp tới
    SoftCard(Modifier.clickable { onClick() }, if (state == 1) BrandLight else Color.White) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(day, Modifier.width(56.dp), color = Gray, fontSize = 12.sp)
            Text(task, Modifier.weight(1f), fontWeight = if (state == 1) FontWeight.Bold else FontWeight.Normal)
            Text(when (state) { 2 -> "✅"; 1 -> "🔵"; else -> "⚪" })
        }
    }

@Composable
fun TabSwitch(a: String, b: String) {
    var sel by remember { mutableStateOf(0) }
    Row(Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(BrandLight).padding(4.dp)) {
        listOf(a, b).forEachIndexed { i, t ->
            Box(Modifier.weight(1f).clip(RoundedCornerShape(10.dp)).background(if (sel == i) Brand else Color.Transparent)
                .clickable { sel = i }.padding(10.dp), contentAlignment = Alignment.Center) {
                Text(t, color = if (sel == i) Color.White else Brand, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

// ---------- 2. Chọn mục tiêu / Tìm kiếm ----------
@Composable
fun GoalsScreen(go: (Screen) -> Unit) = Page {
    Title("Bạn muốn học gì?", "Chọn lĩnh vực hoặc tìm kiếm mục tiêu")
    OutlinedTextField("", {}, Modifier.fillMaxWidth(), placeholder = { Text("Ví dụ: Python, Tiếng Anh, marketing...") },
        shape = RoundedCornerShape(16.dp), leadingIcon = { Text("🔍") })
    var pick by remember { mutableStateOf("Lập trình") }
    listOf("💻" to "Lập trình", "🌐" to "Ngoại ngữ", "🧠" to "Kỹ năng mềm", "📊" to "Kinh doanh",
        "🎨" to "Thiết kế", "🔬" to "Khoa học", "🎵" to "Âm nhạc", "❤️" to "Sức khỏe").chunked(4).forEach { row ->
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            row.forEach { (e, t) ->
                val on = pick == t
                Column(Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(if (on) BrandLight else Color.White)
                    .border(if (on) 2.dp else 0.dp, Brand, RoundedCornerShape(16.dp)).clickable { pick = t }.padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) { Text(e, fontSize = 22.sp); Text(t, fontSize = 10.sp, textAlign = TextAlign.Center) }
            }
        }
    }
    Text("Gợi ý cho bạn", fontWeight = FontWeight.Bold)
    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("Python cho người mới", "IELTS 6.5+", "Lập trình Java", "Data Analysis").forEach { Chip(it) }
    }
    PrimaryButton("Tạo lộ trình với AI  →") { go(Screen.AiAnalysis) }
}

// ---------- 3. AI phân tích mục tiêu ----------
@Composable
fun AiAnalysisScreen(go: (Screen) -> Unit) = Page {
    Box(Modifier.size(64.dp).clip(CircleShape).background(BrandLight).align(Alignment.CenterHorizontally), contentAlignment = Alignment.Center) { Text("🧭", fontSize = 32.sp) }
    Text("AI đang phân tích mục tiêu của bạn...", Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, color = BrandDark)
    Surface(Modifier.align(Alignment.End), RoundedCornerShape(18.dp), color = BrandLight) {
        Text("Tôi muốn học Python trong 3 tháng để có thể tìm việc làm.", Modifier.padding(12.dp))
    }
    SoftCard {
        Text("AI đã hiểu mục tiêu của bạn:", fontWeight = FontWeight.Bold)
        listOf("Môn học" to "Python (Lập trình)", "Mục tiêu" to "Tìm việc làm", "Thời gian" to "3 tháng", "Trình độ hiện tại" to "Người mới")
            .forEach { (k, v) -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { Text("✅", color = Green); Text("$k: $v") } }
    }
    PrimaryButton("Tạo lộ trình học tập  →") { go(Screen.Roadmap) }
    Text("Quá trình này mất vài giây...", Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Gray, fontSize = 12.sp)
}

// ---------- 4. Lộ trình học ----------
@Composable
fun RoadmapScreen(go: (Screen) -> Unit) = Page {
    Title("Lộ trình học tập")
    SoftCard(bg = BrandLight) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("🐍", fontSize = 32.sp)
            Column { Text("Python Developer", fontWeight = FontWeight.Bold, fontSize = 18.sp); Text("3 tháng • Người mới bắt đầu", color = Gray, fontSize = 13.sp) }
        }
    }
    RoadStep(1, "Python Basics", "Đã hoàn thành 8/8 bài học", 2)
    RoadStep(2, "Core Python", "Đang học • 3/10 bài học", 1) { go(Screen.Lesson) }
    RoadStep(3, "Data Structures & Algorithms", "Chưa bắt đầu", 0)
    RoadStep(4, "Web Development", "Chưa bắt đầu", 0)
    RoadStep(5, "Build Project", "Chưa bắt đầu", 0)
    RoadStep(6, "Job Preparation", "Chưa bắt đầu", 0)
    PrimaryButton("Xem chi tiết lộ trình") { go(Screen.StudyPlan) }
}

// ---------- 5. Kế hoạch học tập ----------
@Composable
fun StudyPlanScreen(go: (Screen) -> Unit) = Page {
    Title("Kế hoạch học tập")
    TabSwitch("Tuần", "Tháng")
    Text("‹   Tuần 2 / 12   ›", Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold)
    DayRow("Thứ 2", "Python cơ bản – Biến, kiểu dữ liệu", 2)
    DayRow("Thứ 3", "Vòng lặp và cấu trúc điều kiện", 2)
    DayRow("Thứ 4", "Thực hành bài tập", 1) { go(Screen.Lesson) }
    DayRow("Thứ 5", "Hàm trong Python", 0)
    DayRow("Thứ 6", "Bài tập nâng cao", 0)
    DayRow("Thứ 7", "Ôn tập + Quiz", 0) { go(Screen.Quiz) }
    DayRow("Chủ nhật", "Nghỉ ngơi / Đọc sách", 0)
    SoftCard(bg = BrandLight) { Text("🤖 AI đã điều chỉnh kế hoạch này dựa trên tiến độ và kết quả quiz của bạn.", fontSize = 13.sp) }
}

// ---------- 6. Học – Nội dung bài học ----------
@Composable
fun LessonScreen(go: (Screen) -> Unit) = Page {
    Title("Python cơ bản", "Bài 3/8 • Vòng lặp")
    LinearProgressIndicator(progress = { 0.75f }, Modifier.fillMaxWidth().height(8.dp).clip(CircleShape), color = Brand, trackColor = BrandLight)
    TabSwitch("Lý thuyết", "Thực hành")
    SoftCard {
        Text("Vòng lặp for trong Python", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("Vòng lặp for giúp bạn lặp lại một khối lệnh nhiều lần.", color = Gray)
        Surface(Modifier.fillMaxWidth(), RoundedCornerShape(14.dp), color = BgGray) {
            Text("for i in range(5):\n    print(i)", Modifier.padding(14.dp), fontFamily = FontFamily.Monospace)
        }
    }
    PrimaryButton("Tiếp theo  →") { go(Screen.Quiz) }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        TextButton({}) { Text("🔖 Đánh dấu") }; TextButton({}) { Text("📝 Ghi chú") }; TextButton({ go(Screen.Chat) }) { Text("💬 Hỏi AI") }
    }
}

// ---------- 8. Kết quả & phản hồi AI ----------
@Composable
fun ResultScreen(go: (Screen) -> Unit) = Page {
    Spacer(Modifier.height(8.dp))
    Text("🤖", Modifier.fillMaxWidth(), fontSize = 64.sp, textAlign = TextAlign.Center)
    Text("Tuyệt vời!", Modifier.fillMaxWidth(), fontSize = 26.sp, fontWeight = FontWeight.Bold, color = BrandDark, textAlign = TextAlign.Center)
    Text("Bạn đã hoàn thành bài học này!", Modifier.fillMaxWidth(), color = Gray, textAlign = TextAlign.Center)
    SoftCard {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Kết quả quiz", fontWeight = FontWeight.Bold); Text("80%", color = Green, fontWeight = FontWeight.Bold) }
        Text("4/5 câu đúng", color = Gray)
        LinearProgressIndicator(progress = { 0.8f }, Modifier.fillMaxWidth().height(8.dp).clip(CircleShape), color = Green, trackColor = BrandLight)
    }
    SoftCard(bg = BrandLight) {
        Text("💡 AI gợi ý", fontWeight = FontWeight.Bold)
        Text("Bạn đã làm tốt! Hãy luyện thêm vòng lặp lồng nhau để nắm chắc hơn ở bài tiếp theo.", fontSize = 13.sp)
    }
    PrimaryButton("Xem kế hoạch tiếp theo") { go(Screen.StudyPlan) }
    TextButton({ go(Screen.Home) }, Modifier.fillMaxWidth()) { Text("Quay lại trang chủ") }
}
