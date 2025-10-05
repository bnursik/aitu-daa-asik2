# AITU-DAA-ASIK2 — Min-Heap Implementation (Assignment 2)

## 🧩 Overview

This project implements a **Min-Heap** data structure as part of the _Algorithmic Analysis and Peer Code Review_ assignment (Pair 4 — Heap Data Structures).

**Student A:** Min-Heap with `decreaseKey` and `merge`  
**Student B:** Max-Heap with `increaseKey` and `extractMax`

The goal is to implement, analyze, and empirically validate the algorithm’s efficiency using both theoretical complexity analysis and performance benchmarks.

---

## 📁 Repository Structure

aitu-daa-asik2/
├── src/main/java/
│ ├── algorithms/MinHeap.java
│ ├── metrics/PerformanceTracker.java
│ └── cli/BenchmarkRunner.java
├── src/test/java/
│ └── algorithms/MinHeapTest.java
├── docs/
│ ├── analysis-report.pdf
│ └── performance-plots/
├── pom.xml
└── README.md

---

## ⚙️ Features

- ✅ **Full Min-Heap implementation** with `insert`, `extractMin`, `peekMin`, `decreaseKey`, and `merge`
- ✅ **Metrics collection** — counts comparisons, swaps, array accesses, and allocations
- ✅ **CLI Benchmark Runner** — builds heaps of different sizes and exports CSV
- ✅ **O(n) heapify optimization** for bulk construction
- ✅ **JUnit 5 test suite** covering edge cases and correctness validation

---

## 🧠 Theoretical Complexity

| Operation        | Best Case Ω | Average Case Θ | Worst Case O | Description                            |
| ---------------- | ----------- | -------------- | ------------ | -------------------------------------- |
| Insert           | 1           | log n          | log n        | Bubble-up until heap property restored |
| Extract Min      | 1           | log n          | log n        | Swap root → last → sift-down           |
| Decrease Key     | 1           | log n          | log n        | Adjust value → sift-up                 |
| Merge (heapify)  | n           | n              | n            | Bottom-up heapify O(n)                 |
| Peek Min         | 1           | 1              | 1            | Constant-time access                   |
| Space Complexity | —           | Θ(n)           | —            | In-place array-based structure         |

---

## 🧪 Testing

### Run Unit Tests

```bash
mvn -q -DskipTests=false test
```
