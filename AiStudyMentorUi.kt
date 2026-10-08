package com.example.alstudymentor

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- Theme ----------
val Brand = Color(0xFFD62839)
val BrandDark = Color(0xFFA4161A)
val BrandLight = Color(0xFFFDECEE)
val BgGray = Color(0xFFFAF7F7)
val Gray = Color(0xFF6F6A6B)
val Green = Color(0xFF2E9E5B)
val GreenBg = Color(0xFFE6F6EC)
val Gold = Color(0xFFF5B301)

enum class Screen(val label: String) {
    Welcome(""), Register(""), Setup(""), Home("Trang chủ"), Ask("Hỏi đáp"),
    Solution(""), Chat(""), Quiz("Luyện tập"), Library("Thư viện"), Progress("Tiến độ"),
    Goals(""), AiAnalysis(""), Roadmap("Lộ trình"), StudyPlan(""), Lesson(""), Result("")
}

val tabs = listOf(
    Screen.Home to "🏠", Screen.Ask to "💬", Screen.Roadmap to "🧭",
    Screen.Library to "📚", Screen.Progress to "📈"
)

// ---------- Root ----------
@Composable
fun AiStudyMentorApp() {
    var screen by remember { mutableStateOf(Screen.Welcome) }
    val go: (Screen) -> Unit = { screen = it }
    MaterialTheme(colorScheme = lightColorScheme(primary = Brand)) {
        Scaffold(
            containerColor = BgGray,
            bottomBar = {
                if (screen in tabs.map { it.first }) NavigationBar(containerColor = Color.White) {
                    tabs.forEach { (s, icon) ->
                        NavigationBarItem(
                            selected = screen == s, onClick = { go(s) },
                            icon = { Text(icon) }, label = { Text(s.label, fontSize = 11.sp) },
                            colors = NavigationBarItemDefaults.colors(indicatorColor = BrandLight, selectedTextColor = Brand)
                        )
                    }
                }
            }
        ) { pad ->
            Box(Modifier.padding(pad).fillMaxSize()) {
                when (screen) {
                    Screen.Welcome -> WelcomeScreen(go)
                    Screen.Register -> RegisterScreen(go)
                    Screen.Setup -> SetupScreen(go)
                    Screen.Home -> HomeScreen(go)
                    Screen.Ask -> AskScreen(go)
                    Screen.Solution -> SolutionScreen(go)
                    Screen.Chat -> ChatScreen(go)
                    Screen.Quiz -> QuizScreen(go)
                    Screen.Library -> LibraryScreen(go)
                    Screen.Progress -> ProgressScreen(go)
                    Screen.Goals -> GoalsScreen(go)
                    Screen.AiAnalysis -> AiAnalysisScreen(go)
                    Screen.Roadmap -> RoadmapScreen(go)
                    Screen.StudyPlan -> StudyPlanScreen(go)
                    Screen.Lesson -> LessonScreen(go)
                    Screen.Result -> ResultScreen(go)
                }
            }
        }
    }
}

// ---------- Reusable components ----------
@Composable
fun Page(content: @Composable ColumnScope.() -> Unit) = Column(
    Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp), content = content
)

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit) = Button(
    onClick, Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp),
    colors = ButtonDefaults.buttonColors(containerColor = BrandDark)
) { Text(text, fontWeight = FontWeight.Bold) }

@Composable
fun SoftCard(modifier: Modifier = Modifier, bg: Color = Color.White, content: @Composable ColumnScope.() -> Unit) =
    Card(modifier.fillMaxWidth(), RoundedCornerShape(20.dp), CardDefaults.cardColors(containerColor = bg),
        CardDefaults.cardElevation(1.dp)) { Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp), content = content) }

@Composable
fun Title(t: String, sub: String? = null) = Column {
    Text(t, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = BrandDark)
    sub?.let { Text(it, color = Gray, fontSize = 14.sp) }
}

@Composable
fun Chip(text: String, selected: Boolean = false, onClick: () -> Unit = {}) = Surface(
    Modifier.clickable { onClick() }, RoundedCornerShape(50),
    color = if (selected) Brand else BrandLight
) { Text(text, Modifier.padding(horizontal = 14.dp, vertical = 8.dp), color = if (selected) Color.White else Brand, fontSize = 13.sp) }

@Composable
fun Field(label: String, secure: Boolean = false) {
    var v by remember { mutableStateOf("") }
    OutlinedTextField(v, { v = it }, Modifier.fillMaxWidth(), label = { Text(label) },
        shape = RoundedCornerShape(14.dp),
        visualTransformation = if (secure) androidx.compose.ui.text.input.PasswordVisualTransformation()
        else androidx.compose.ui.text.input.VisualTransformation.None)
}

@Composable
fun ProgressRow(name: String, pct: Int) = SoftCard {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(name, fontWeight = FontWeight.SemiBold); Text("$pct%", color = Brand, fontWeight = FontWeight.Bold)
    }
    LinearProgressIndicator(progress = { pct / 100f }, Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
        color = Brand, trackColor = BrandLight)
}

@Composable
fun StatCard(value: String, label: String, m: Modifier) = Card(m, RoundedCornerShape(16.dp), CardDefaults.cardColors(containerColor = Color.White)) {
    Column(Modifier.padding(12.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Brand); Text(label, fontSize = 11.sp, color = Gray)
    }
}

// ---------- 1. Welcome ----------
@Composable
fun WelcomeScreen(go: (Screen) -> Unit) = Page {
    Spacer(Modifier.height(16.dp))
    Box(Modifier.size(80.dp).clip(RoundedCornerShape(24.dp)).background(Brand).align(Alignment.CenterHorizontally),
        contentAlignment = Alignment.Center) { Text("🧭", fontSize = 40.sp) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Learning GPS AI", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = BrandDark)
        Text("Học bất cứ điều gì, ở mọi trình độ", color = Gray)
    }
    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("Toán", "Vật lý", "Hóa học", "Lập trình", "Ngoại ngữ").forEach { Chip(it) }
    }
    SoftCard {
        Text("📷 Ảnh chụp bài tập Đạo hàm", fontWeight = FontWeight.SemiBold)
        Text("Tìm cực trị hàm số f(x) = x³ - 3x + 2", color = Gray)
        SoftCard(bg = GreenBg) { Text("f'(x) = 3x² - 3 = 0 ⇒ x = ±1\nCực đại tại x = -1, cực tiểu tại x = 1", color = Green) }
    }
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        SoftCard(Modifier.weight(1f)) { Text("📸 Chụp ảnh bài tập", fontWeight = FontWeight.SemiBold); Text("Nhận diện đề chuẩn 99%", fontSize = 12.sp, color = Gray) }
        SoftCard(Modifier.weight(1f)) { Text("💬 Hỏi đáp phản hồi", fontWeight = FontWeight.SemiBold); Text("AI giải thích tức thì", fontSize = 12.sp, color = Gray) }
    }
    PrimaryButton("Bắt đầu ngay  →") { go(Screen.Register) }
    Text("Đã có tài khoản? Đăng nhập", Modifier.fillMaxWidth().clickable { go(Screen.Home) }, color = Brand,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center)
}

// ---------- 2. Register ----------
@Composable
fun RegisterScreen(go: (Screen) -> Unit) = Page {
    Title("Tạo tài khoản học tập", "Cá nhân hóa trải nghiệm học tập của bạn")
    Field("Họ và tên học sinh"); Field("Email / Số điện thoại")
    var level by remember { mutableStateOf("THPT") }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("THCS", "THPT", "Đại học").forEach { Chip(it, level == it) { level = it } } }
    Field("Mật khẩu", true)
    LinearProgressIndicator(progress = { 0.7f }, Modifier.fillMaxWidth().height(6.dp).clip(CircleShape), color = Green, trackColor = BrandLight)
    Text("Độ mạnh: Khá", fontSize = 12.sp, color = Green)
    Field("Xác nhận mật khẩu", true)
    var ok by remember { mutableStateOf(true) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(ok, { ok = it }); Text("Tôi đồng ý Điều khoản dịch vụ và Chính sách bảo mật", fontSize = 13.sp)
    }
    PrimaryButton("Tạo tài khoản ngay  →") { go(Screen.Setup) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("Google", "Apple ID", "SSO Trường").forEach {
            OutlinedButton({}, Modifier.weight(1f), shape = RoundedCornerShape(14.dp)) { Text(it, fontSize = 12.sp) }
        }
    }
}

// ---------- 3. Setup ----------
@Composable
fun SetupScreen(go: (Screen) -> Unit) = Page {
    Title("Cá nhân hóa trải nghiệm học tập của bạn", "Bước 1/2")
    Text("1. Chọn cấp học", fontWeight = FontWeight.SemiBold)
    var level by remember { mutableStateOf(1) }
    listOf("THCS" to "Lớp 6-9", "THPT" to "Lớp 10-12", "Đại học & Cao đẳng" to "").forEachIndexed { i, (t, s) ->
        SoftCard(Modifier.border(if (level == i) 2.dp else 0.dp, Brand, RoundedCornerShape(20.dp)).clickable { level = i }) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column { Text(t, fontWeight = FontWeight.Bold); if (s.isNotEmpty()) Text(s, color = Gray, fontSize = 13.sp) }
                if (i == 1) Chip("Trọng tâm", true)
            }
        }
    }
    Text("2. Chọn môn học quan tâm", fontWeight = FontWeight.SemiBold)
    val subjects = listOf("➗ Toán học", "🔬 Khoa học", "💻 Lập trình", "🏛 Lịch sử", "🌐 Ngôn ngữ")
    val picked = remember { mutableStateListOf("➗ Toán học") }
    subjects.chunked(2).forEach { row ->
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            row.forEach { s ->
                val on = s in picked
                SoftCard(Modifier.weight(1f).clickable { if (on) picked.remove(s) else picked.add(s) }, if (on) BrandLight else Color.White) {
                    Text(if (on) "✅ $s" else s, fontWeight = FontWeight.SemiBold)
                }
            }
            if (row.size == 1) Spacer(Modifier.weight(1f))
        }
    }
    PrimaryButton("Tiếp tục vào Trang chủ  →") { go(Screen.Home) }
}

// ---------- 4. Home ----------
@Composable
fun HomeScreen(go: (Screen) -> Unit) = Page {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(Modifier.size(44.dp).clip(CircleShape).background(Brand), contentAlignment = Alignment.Center) { Text("MH", color = Color.White) }
        Column(Modifier.weight(1f)) { Text("Learning GPS AI", fontSize = 12.sp, color = Gray); Text("Chào Minh Hoàng!", fontWeight = FontWeight.Bold, fontSize = 18.sp) }
        Chip("🔥 5 ngày"); Chip("⭐ 1,450 XP"); Text("🔔")
    }
    Card(Modifier.fillMaxWidth(), RoundedCornerShape(24.dp), CardDefaults.cardColors(containerColor = Brand)) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("AI trợ giảng 24/7", color = Color.White.copy(0.8f), fontSize = 12.sp)
            Text("Hỏi AI ngay hôm nay!", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button({ go(Screen.Ask) }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Brand)) { Text("📷 Chụp bài tập") }
                Button({ go(Screen.Chat) }, colors = ButtonDefaults.buttonColors(containerColor = BrandDark)) { Text("💬 Hỏi AI ngay") }
            }
        }
    }
    Button({ go(Screen.Goals) }, Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BrandDark)) { Text("🧭 Tạo lộ trình học với AI") }
    OutlinedTextField("", {}, Modifier.fillMaxWidth(), placeholder = { Text("Tìm bài giải, chủ đề ôn tập, công thức...") },
        shape = RoundedCornerShape(16.dp), leadingIcon = { Text("🔍") })
    Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        listOf("Tất cả", "Toán", "Lý", "Lập trình", "Anh").forEachIndexed { i, s -> Chip(s, i == 0) }
    }
    SoftCard(bg = BrandLight) {
        Text("🎯 Mục tiêu hôm nay", fontWeight = FontWeight.Bold)
        Text("Hoàn thành 2/3 bài tập  (+50 XP)", color = Gray)
        LinearProgressIndicator(progress = { 2 / 3f }, Modifier.fillMaxWidth().height(8.dp).clip(CircleShape), color = Brand, trackColor = Color.White)
    }
    Text("Môn học đang theo dõi", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    ProgressRow("Toán Giải tích 12", 68); ProgressRow("Vật lý 12", 45); ProgressRow("Python cơ bản", 80); ProgressRow("Tiếng Anh THPT", 50)
    Text("Hoạt động gần đây", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    SoftCard { Text("Tích phân từng phần – Toán 12"); Text("2 giờ trước • +20 XP", color = Gray, fontSize = 12.sp) }
}

// ---------- 5. Ask ----------
@Composable
fun AskScreen(go: (Screen) -> Unit) = Page {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Title("Đặt câu hỏi cho AI Mentor"); Chip("Pro v4.0", true) }
    SoftCard {
        Text("Cấu hình câu trả lời", fontWeight = FontWeight.Bold)
        var mode by remember { mutableStateOf(0) }
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("Toán", "Lý", "Hóa", "Lập trình").forEachIndexed { i, s -> Chip(s, i == 0) } }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Chip("Chi tiết từng bước", mode == 0) { mode = 0 }; Chip("Chỉ đáp án & gợi ý", mode == 1) { mode = 1 }
        }
    }
    OutlinedTextField("", {}, Modifier.fillMaxWidth().height(120.dp), placeholder = { Text("Nhập đề bài hoặc câu hỏi...") },
        shape = RoundedCornerShape(16.dp), trailingIcon = { Text("🎤") })
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("∫dx", "√x", "xⁿ", "π").forEach { Chip(it) } }
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedButton({}, Modifier.weight(1f), shape = RoundedCornerShape(14.dp)) { Text("📷 Chụp ảnh") }
        OutlinedButton({}, Modifier.weight(1f), shape = RoundedCornerShape(14.dp)) { Text("🖼️ Từ thư viện") }
    }
    Box(Modifier.fillMaxWidth().height(140.dp).clip(RoundedCornerShape(20.dp)).background(BrandLight), contentAlignment = Alignment.Center) {
        Text("Ảnh bài toán • Độ rõ nét 98%", color = Brand)
    }
    PrimaryButton("Gửi câu hỏi cho AI Mentor") { go(Screen.Solution) }
}

// ---------- 6. Solution ----------
@Composable
fun SolutionScreen(go: (Screen) -> Unit) = Page {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Title("Lời giải AI Mentor"); Text("🔖  📤", fontSize = 20.sp) }
    SoftCard { Text("Đề bài", color = Gray, fontSize = 12.sp); Text("Tính ∫ x·sin(x) dx") }
    SoftCard(bg = GreenBg) { Text("Kết quả", color = Green, fontSize = 12.sp); Text("F(x) = -x·cos(x) + sin(x) + C", color = Green, fontWeight = FontWeight.Bold, fontSize = 18.sp) }
    listOf("Bước 1: Chọn u = x, dv = sin(x)dx", "Bước 2: du = dx, v = -cos(x)", "Bước 3: Áp dụng ∫u dv = uv - ∫v du").forEach { SoftCard { Text(it) } }
    SoftCard(bg = BrandLight) { Text("🧒 Giải thích siêu đơn giản (ELI5)", fontWeight = FontWeight.Bold); Text("Giống bóc từng lớp vỏ bánh đa tầng: bóc một lớp, phần còn lại dễ hơn.", color = Gray) }
    SoftCard { Text("⚡ Cách giải nhanh", fontWeight = FontWeight.Bold); Text("Phương pháp cột múa (đạo hàm – nguyên hàm xen kẽ).", color = Gray) }
    SoftCard { Text("⚠️ Lỗi thường gặp", fontWeight = FontWeight.Bold); Text("Quên dấu âm của v = -cos(x).", color = Gray) }
    PrimaryButton("✨ Tạo bài tập tương tự để luyện tập") { go(Screen.Quiz) }
    TextButton({ go(Screen.Chat) }, Modifier.fillMaxWidth()) { Text("Thảo luận tiếp với AI") }
}

// ---------- 7. Chat ----------
@Composable
fun ChatScreen(go: (Screen) -> Unit) = Column(Modifier.fillMaxSize()) {
    Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Title("Toán Giải tích 12", "Thảo luận cùng AI Mentor")
        Surface(Modifier.align(Alignment.End), RoundedCornerShape(18.dp), color = Brand) {
            Text("Tại sao chọn u = x mà không phải u = sin(x)?", Modifier.padding(12.dp), color = Color.White)
        }
        SoftCard {
            Text("🤖 AI Mentor", fontWeight = FontWeight.Bold, color = Brand)
            SoftCard(bg = GreenBg) { Text("✅ Chọn u = x: đạo hàm đơn giản dần, tích phân gọn.", color = Green) }
            SoftCard(bg = Color(0xFFFDECEC)) { Text("❌ Chọn u = sin(x): ∫ phức tạp hơn, bài toán rối thêm.", color = Color(0xFFC62828)) }
            Text("Quy tắc vàng: \"Nhất log, nhì đa, tam lượng, tứ mũ\"", fontWeight = FontWeight.SemiBold, color = BrandDark)
            SoftCard(bg = BrandLight) { Row(verticalAlignment = Alignment.CenterVertically) { Text("▶️  Nghe AI giảng (HQ)  •  0:45s", color = Brand) } }
        }
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Chip("Cho ví dụ khác"); Chip("Khi nào dùng cột múa?")
        }
    }
    Row(Modifier.fillMaxWidth().background(Color.White).padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField("", {}, Modifier.weight(1f), placeholder = { Text("Nhập tin nhắn...") }, shape = RoundedCornerShape(24.dp))
        Spacer(Modifier.width(8.dp))
        Box(Modifier.size(48.dp).clip(CircleShape).background(Brand).clickable { }, contentAlignment = Alignment.Center) { Text("➤", color = Color.White) }
    }
}

// ---------- 8. Quiz ----------
@Composable
fun QuizScreen(go: (Screen) -> Unit) = Page {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Title("Luyện tập cùng AI", "Câu 3/5 • 60%"); Column(horizontalAlignment = Alignment.End) { Text("⏱ 02:45", fontWeight = FontWeight.Bold); Text("+20 XP", color = Green) }
    }
    LinearProgressIndicator(progress = { 0.6f }, Modifier.fillMaxWidth().height(8.dp).clip(CircleShape), color = Brand, trackColor = BrandLight)
    SoftCard { Text("Đạo hàm của f(x) = x³ là:", fontWeight = FontWeight.SemiBold, fontSize = 17.sp) }
    listOf("A. 3x²", "B. x²", "C. 3x", "D. x³/3").forEachIndexed { i, a ->
        val right = i == 0
        SoftCard(Modifier.border(if (right) 2.dp else 0.dp, Green, RoundedCornerShape(20.dp)), if (right) GreenBg else Color.White) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(a); if (right) Text("✅") }
        }
    }
    SoftCard(bg = GreenBg) { Text("Chính xác! +20 XP", color = Green, fontWeight = FontWeight.Bold); Text("Mẹo nhớ: (xⁿ)' = n·xⁿ⁻¹ — đưa mũ xuống làm hệ số, giảm mũ đi 1.") }
    PrimaryButton("Câu hỏi tiếp theo  →") { go(Screen.Result) }
}

// ---------- 9. Library ----------
@Composable
fun LibraryScreen(go: (Screen) -> Unit) = Box(Modifier.fillMaxSize()) {
    Page {
        Title("Thư viện & Lịch sử")
        SoftCard(bg = Brand) {
            Text("AI Đề xuất", color = Color.White.copy(0.8f), fontSize = 12.sp)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Ôn tập 5 câu hỏi trọng tâm", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Button({ go(Screen.Quiz) }, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Brand)) { Text("Bắt đầu ›") }
            }
        }
        OutlinedTextField("", {}, Modifier.fillMaxWidth(), placeholder = { Text("Tìm trong thư viện...") }, shape = RoundedCornerShape(16.dp), leadingIcon = { Text("🔍") })
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("Đã lưu", "Tất cả", "Bộ tự luyện").forEachIndexed { i, s -> Chip(s, i == 0) } }
        listOf("Toán 12 – Tích phân từng phần" to "u = x, dv = sin(x)dx ⇒ F(x) = ...", "Vật lý 12 – Dao động điều hòa" to "Chu kỳ T = 2π√(m/k)...", "Python – Vòng lặp for" to "Duyệt list với range()...").forEach { (t, s) ->
            SoftCard {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(t, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f)); Text("⭐", color = Gold) }
                Text(s, color = Gray, fontSize = 13.sp); Text("2 giờ trước", color = Gray, fontSize = 11.sp)
            }
        }
        Spacer(Modifier.height(64.dp))
    }
    Button({ go(Screen.Quiz) }, Modifier.align(Alignment.BottomCenter).padding(16.dp).fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandDark)) { Text("Ôn tập nhanh các bài đã lưu (18)") }
}

// ---------- 10. Progress ----------
@Composable
fun ProgressScreen(go: (Screen) -> Unit) = Page {
    Title("Tiến độ & Bảng xếp hạng")
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        StatCard("128", "Câu đã giải", Modifier.weight(1f)); StatCard("92%", "Độ chính xác", Modifier.weight(1f)); StatCard("1,450", "XP", Modifier.weight(1f))
    }
    SoftCard(bg = BrandLight) { Text("💡 Lời khuyên từ AI Mentor", fontWeight = FontWeight.Bold); Text("Môn Toán Giải tích tăng +15% tuần này. Hãy luyện thêm dạng tích phân từng phần!", color = Gray) }
    Text("Bảng xếp hạng tuần", fontWeight = FontWeight.Bold, fontSize = 16.sp)
    listOf("🥇 Ngọc Anh" to "2,310 XP", "🥈 Quang Huy" to "2,050 XP", "🥉 Thu Hà" to "1,880 XP").forEach { (n, x) ->
        SoftCard { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(n, fontWeight = FontWeight.SemiBold); Text(x, color = Brand) } }
    }
    SoftCard(Modifier.border(2.dp, Brand, RoundedCornerShape(20.dp)), BrandLight) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("#8  Minh Hoàng (Bạn)", fontWeight = FontWeight.Bold); Text("1,450 XP", color = Brand) }
        Text("Còn 120 XP nữa để vào Top 5", color = Gray, fontSize = 13.sp)
    }
    PrimaryButton("Luyện tập ngay  →") { go(Screen.Quiz) }
}
