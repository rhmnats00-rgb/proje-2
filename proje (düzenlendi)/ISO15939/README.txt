ISO 15939 Measurement Process Simulator
========================================

Ogrenci Adi  : [Adinizi Yazin]
Ogrenci No   : [Numaranizi Yazin]
Ders         : Software Project II

-----------------------------------------
DERLEME (Compile) - Command Line
-----------------------------------------

1. Projenin kök dizinine gidin (ISO15939/ klasörü).

2. Çıktı klasörünü oluşturun:
   mkdir out

3. Tüm .java dosyalarını derleyin:

   Windows:
   javac -d out -sourcepath src src\Main.java src\model\*.java src\data\*.java src\gui\*.java

   Linux / macOS:
   javac -d out -sourcepath src src/Main.java src/model/*.java src/data/*.java src/gui/*.java

-----------------------------------------
ÇALIŞTIRMA (Run)
-----------------------------------------

   java -cp out Main

-----------------------------------------
PROJE YAPISI
-----------------------------------------

ISO15939/
├── src/
│   ├── Main.java                    ← Giriş noktası
│   ├── model/
│   │   ├── AppState.java            ← Oturum durumu (MVC Model)
│   │   ├── Metric.java              ← Metrik verisi + skor hesabı
│   │   ├── Dimension.java           ← Boyut (dimension) + ağırlıklı ortalama
│   │   └── Scenario.java            ← Senaryo (boyutlar listesi)
│   ├── data/
│   │   └── ScenarioRepository.java  ← Hard-coded senaryo veritabanı
│   └── gui/
│       ├── MainFrame.java           ← Ana pencere, CardLayout wizard
│       ├── StepIndicatorPanel.java  ← Üst adım göstergesi
│       ├── Step1ProfilePanel.java   ← Adım 1: Profil
│       ├── Step2DefinePanel.java    ← Adım 2: Define
│       ├── Step3PlanPanel.java      ← Adım 3: Plan (salt okunur)
│       ├── Step4CollectPanel.java   ← Adım 4: Collect (skor hesabı)
│       ├── Step5AnalysePanel.java   ← Adım 5: Analyse
│       └── RadarChartPanel.java     ← BONUS: Radar chart (Graphics2D)
└── out/                             ← Derlenmiş .class dosyaları (derleme sonrası oluşur)

-----------------------------------------
ÖZELLIKLER
-----------------------------------------
• Java SE 17+ (harici kütüphane yok)
• CardLayout wizard yapısı (5 adım)
• MVC deseni: model/gui ayrımı
• Hard-coded senaryo veritabanı (Health x2, Education x2)
• Skor formülü: Higher → 1+(v-min)/(max-min)*4, Lower → 5-(v-min)/(max-min)*4
• 0.5'lik yuvarlama
• Boyut ağırlıklı ortalama: Σ(score×coeff)/Σcoeff
• JProgressBar ile boyut görselleştirmesi
• BONUS: Graphics2D ile radar (örümcek) chart
• Gap analizi: en düşük boyut, gap değeri, kalite etiketi
• Doğrulama uyarıları (Step 1 boş alan kontrolü)
• Restart butonu
