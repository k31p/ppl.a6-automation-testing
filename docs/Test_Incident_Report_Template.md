# Test Incident Report Template

## Informasi Umum

| Field | Value |
|-------|-------|
| **Incident ID** | INC-XXX |
| **Date Reported** | YYYY-MM-DD |
| **Reported By** | Nama Anggota |
| **Test Case ID** | TC-FRXX-XX |
| **Severity** | Critical / High / Medium / Low |
| **Priority** | P1 / P2 / P3 / P4 |

---

## Deskripsi Bug

### Summary
[Ringkasan singkat bug yang ditemukan]

### Steps to Reproduce
1. [Langkah 1]
2. [Langkah 2]
3. [Langkah 3]

### Expected Result
[Hasil yang diharapkan]

### Actual Result
[Hasil yang sebenarnya terjadi]

---

## Evidence

### Screenshot
[Lampiran screenshot error]

### Log/Stack Trace
```
[Error log atau stack trace]
```

### Environment
- Browser: Chrome XX.X
- OS: Windows 10/11
- Java Version: 11
- Selenium Version: 4.15.0

---

## Analysis

### Root Cause
[Analisis penyebab bug]

### Impact
[Dampak bug terhadap sistem]

---

## Resolution

### Status
- [ ] New
- [ ] Assigned
- [ ] In Progress
- [ ] Fixed
- [ ] Verified
- [ ] Closed

### Notes
[Catatan tambahan]

---

## Contoh Incident Report

### Incident ID: INC-001

| Field | Value |
|-------|-------|
| **Incident ID** | INC-001 |
| **Date Reported** | 2026-06-03 |
| **Reported By** | Anggota C |
| **Test Case ID** | TC-FR01-05 |
| **Severity** | Medium |
| **Priority** | P2 |

### Summary
Error message tidak muncul saat login dengan kredensial invalid

### Steps to Reproduce
1. Buka halaman login JTKLearn
2. Masukkan email: "invalid@example.com"
3. Masukkan password: "wrongpassword"
4. Klik tombol Login

### Expected Result
Muncul pesan error "Invalid credentials"

### Actual Result
Halaman loading terus menerus tanpa pesan error

### Root Cause Analysis
Kemungkinan:
1. Selector untuk error message tidak tepat
2. Response time server lambat
3. Element belum loaded saat assertion dijalankan

### Suggested Fix
1. Update locator untuk error message
2. Tambahkan explicit wait untuk element
3. Verifikasi response dari server

---

## Incident Report Format untuk Setiap Bug

Gunakan template ini untuk melaporkan bug yang ditemukan saat testing:

1. **Screenshot** - Ambil screenshot saat error terjadi
2. **Stack Trace** - Copy error log dari console
3. **Steps to Reproduce** - Tulis langkah-langkah yang menyebabkan error
4. **Expected vs Actual** - Bandingkan hasil yang diharapkan dengan aktual
5. **Environment Details** - Cantumkan detail environment testing
