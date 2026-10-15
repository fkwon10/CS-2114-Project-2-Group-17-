# CS-2114-Project-2-Group-17-
Duty and Downtime 
## Reflection
What changed from your specification? Classes, method signatures, where validation lives, test cases — what did the spec say, what did you build instead, and why?
What changed in the LinkedChain? Anything you added to or modified in the provided class and why your application needed it, or that you used it unchanged.
What happened to your unknowns? Of the things you listed in your scope as not knowing how to do, which did you figure out, and which are still open?
What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing in this prototype.
## Next steps
## Next Steps

### 1. What Is Left to Finish the MVP (In Order)
Our prototype already has working task creation, scanner error handling, removal logic, and the Work-Then-Play recommendation system. To bring the prototype to final submission quality, we will complete the remaining work in this order:

1. **Replace Index-Based Loops with an Iterator (`DutyRecommendation.java`):**
   - Update `searchTasks`, `toString`, and `countActive` methods to traverse chains using an `Iterator<T>` instead of calling `.get(i)` inside for-loops, improving performance from O(n^2) to O(n).
2. **Create the Missing `LinkedChainTest.java` Test Suite:**
   - Build a comprehensive JUnit 5 test class to test all generic pointer operations in `LinkedChain<T>` directly.
3. **Refine Output Formatting (`Main.java`):**
   - Polish spacing and banner headers when displaying completed tasks and search results to ensure clear terminal readability.
4. **Comprehensive Checkstyle & Javadoc Audit:**
   - Audit all classes to ensure every method has complete Javadoc comments and passes all local Checkstyle rules without warnings.

---

### 2. Stretch Goals (Scope Review)
We reviewed every stretch goal listed in our original scope document:

* **Committed / In-Progress Stretch Goals:**
  - **Search and Filtering (Already Implemented):** We already built keyword searching across both academic and hobby task chains in `DutyRecommendation.searchTasks()` and hooked it up to Option 7 in `Main.java`.
  - **Point System (Attempting if MVP finishes on schedule):** We plan to add a simple gamified point tracker where completing an academic task awards points based on its estimated hours (e.g., 10 points per hour), and taking a break with a hobby spends earned points. This directly reinforces the "Work-Then-Play" balance.
* **Officially Dropped Stretch Goals:**
  - To prevent scope creep and ensure our data structure tests remain our top priority, we are officially dropping:
    - *CSV Import/Export*
    - *Priority System (low/med/high)*
    - *Custom Break Lengths*
    - *Weekly Summaries*
    - *Task Sorting*

---

### 3. New Tests Needed (Methods & Bad Inputs)
We will add new test cases covering pointer edge cases, the search feature, and point calculation logic:

* **`LinkedChainTest` (Generic Storage Tests):**
  - `testRemoveHeadNode()`: Normal test removing the very first item in the chain.
  - `testRemoveMiddleAndLastNode()`: Normal test verifying pointer rewiring when deleting interior or tail nodes.
  - `testRemoveFromEmptyChain()`: Bad input; verifies removing from an empty chain returns `false` or `null` without throwing a `NullPointerException`.
  - `testRemoveNonExistentItem()`: Bad input; verifies searching for an item not in the chain returns `false` and leaves chain size unchanged.
  - `testAddNullThrowsException()`: Bad input; verifies `add(null)` and `addToEnd(null)` throw `IllegalArgumentException`.
  - `testGetInvalidIndex()`: Bad input; verifies negative indices and indices `>= size` throw `IndexOutOfBoundsException`.
* **`testDutyRecommendation` (Search, Duplicates & Points):**
  - `testSearchKeywordFound()`: Normal search test matching tasks by course code, assignment name, category, or description.
  - `testSearchKeywordNotFound()`: Bad input; verifies searching for a word not present returns a clean "No tasks found" message.
  - `testSearchBlankQuery()`: Bad input; verifies null or whitespace queries return a prompt error rather than crashing.
  - `testEarnPointsOnAcademicCompletion()`: Normal test verifying points increase proportionately when homework is completed.
  - `testSpendPointsOnHobbyCompletion()`: Normal test verifying points decrease when a hobby is finished.
  - `testSpendPointsInsufficientBalance()`: Bad input; verifies a user cannot complete a hobby requiring more points than their current earned balance.

---

### 4. Who Does What (Team Ownership)

| Remaining Task | Description | Owner |
| :--- | :--- | :--- |
| **1. LinkedChain Unit Tests** | Build `LinkedChainTest.java` covering head, middle, and tail removals, empty chains, and null insertions. | **Francis** |
| **2. Iterator Optimization & Search Tests** | Update `DutyRecommendation.java` to use iterators instead of index loops, and write `testDutyRecommendation` search tests. | **Irmuun** |
| **3. Point System & UI Polish** | Implement the optional point ledger in `DutyRecommendation`/`Main.java`, write point tests, and perform final Checkstyle verifications. | **Chandrika** |
## Attribution
 a table with one row per class and test file listing who worked on it and how (wrote, revised, reviewed, tested, GenAI-assisted, …), plus a note on the way in which you used GenAI, if at all.
| Class | Who Worked on it | How |
| :--- | :---: | :--- |
| **AcademicTask**| Chandrika | Wrote the class and revised it |
| **AcademicTaskTest** | Chandrika | Wrote the test file and tested the class |
| **DutyRecommendation** | Irmuun | Wrote the class and reviewed it. GenAI assisted with the implementation of the class |
| **HobbyTask** | Chandrika | Wrote the class and revised it |
| **HobbyTaskTest** | Chandrika | Wrote the test file and tested the class |
| **LinkedChain** | Provided | N/A |
| **Main** | Francis | Wrote the Main class and reviewed it |
| **testDutyRecommendation** | Irmuun | Wrote the test class and tested the class. GenAI assisted with making the test cases |
