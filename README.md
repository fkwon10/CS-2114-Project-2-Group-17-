# CS-2114-Project-2-Group-17-
Duty and Downtime 

## How to Download and Run in Eclipse
1. Go to the main page of our GitHub repo
2. Click the green **Code** button, then click **Download ZIP**.
3. Open Eclipse and go to **File → Import → General → Existing Projects into Workspace**, then click **Next**.
4. Click **Browse** next to "Select root directory" and choose the downloaded file that directly contains `.project` and `src`.
5. Make sure the project is checked in the Projects list, then click **Finish**. If Eclipse says a project with that name already exists, delete or rename the old one first.
6. To run the program, right-click `Main.java` in Package Explorer and choose **Run As → Java Application**. The menu appears in the Console tab, where you type a number from 1 to 8.
7. To run the tests, right-click the `dutydowntime` package and choose **Run As → JUnit Test**. A green bar means all tests passed.

## Reflection
What changed from our specification. For the most part we built what the spec described. LinkedChain stores things, AcademicTask and HobbyTask hold the data, DutyRecommendation owns both chains and the suggestNextHobby flag, and Main runs the menu. Most of the changes are small. The spec called the lookup method getEntry, but the LinkedChain we were given calls it get, so we used that. In Main we named the controller controller instead of recommendation, and we broke the menu code into helper methods like handleComplete and handleRemove, which the spec never listed. We also ended up building the search feature and menu option 7 even though search was left out of the MVP.

Validation is mostly where we said it would be. Main re-prompts when the input is bad, the constructors throw IllegalArgumentException if something still gets through, and the remove methods return false instead of throwing. We added a few extra checks along the way. The constructors reject NaN and infinite hours, and the remove methods return false on null and ignore case and extra spaces. One thing is different from the spec: Main doesn’t catch IndexOutOfBoundsException. readPosition already keeps the number in range, so the exception would only happen if someone called the controller directly.

The recommendation messages changed too. The spec said we’d show a “nothing left” message when the other category was empty. What we did instead is suggest an unfinished task from the same category (like “No Hobby Tasks left. Suggested Academic Task...”), and only say “No tasks left” when everything is done. The display is also a little different from the spec, because toString() prints [] for an empty list instead of “none” and doesn’t show the active counts. On tests, we have AcademicTaskTest, HobbyTaskTest and DutyRecommendationTest, but nothing for LinkedChain or Main yet.

What changed in LinkedChain. We didn’t change it. Our spec listed an iterator() method, but the class we were given doesn’t have one, so we never actually modified it. We only use add, remove(T), get, size and isEmpty. The rest (addToEnd, count, toArray, contains, clear and remove()) go unused. Because we loop with get(i), a lot of our methods are slower than the O(n) we claimed in the spec. Adding an iterator is the first thing we plan to change in LinkedChain.

What happened to our unknowns. We figured out most of them. The boolean flag works: finishing an academic task sets it to true, finishing a hobby sets it to false, and getNextRecommendation uses it to decide which list to pull from. Finished tasks don’t get suggested again, and completing the same task twice just tells you it’s already done. Duplicates are kept as separate nodes, and removing one only removes the first match. For the pointer rewiring worry, the provided LinkedChain already handles removing the head, middle and last nodes, so we didn’t have to write that ourselves, but we haven’t tested it directly since we don’t have a LinkedChainTest yet. We never tried CSV files because we dropped that stretch goal, so that one is still open.

What doesn’t work yet. The biggest usability problem is that “Complete” and “Remove” ask for an item number, but the task list doesn’t show numbers, so a user can’t tell which number to type. Several methods (countActive, toString, searchTasks and getNextRecommendation) loop with get(i), which makes them O(n²). We still need tests for LinkedChain and Main, and the active counts aren’t shown anywhere in the menu. Due dates are just text with no check that they’re real dates, and the point system isn’t done.

Two things in your other documents don’t match this, so you may want to fix them. The revised scope says the point system was dropped, but your Next Steps says you’ll try it if there’s time. The spec’s Data & State section also says suggestNextHobby starts as true, but the code and the constructor description both say false.

## Next Steps

### 1. Finishing the MVP (Remaining Features & Priority Order)
Our prototype already has working task creation, scanner error handling, removal logic, and the Work-Then-Play recommendation system. To transition our current working prototype into a feature-complete Minimum Viable Product (MVP), we will complete the remaining work in this order:

1. Replace Index-Based Loops with an Iterator (DutyRecommendation.java): Update searchTasks, toString, and countActive methods to traverse chains using an Iterator instead of calling get(i) inside for-loops, improving performance from O(n^2) to O(n).
2. Create the Missing LinkedChainTest.java Test Suite: Build a comprehensive JUnit 5 test class to test all generic pointer operations in LinkedChain directly.
3. Refine Output Formatting (Main.java): Polish spacing and banner headers when displaying completed tasks and search results to ensure clear terminal readability.
4. Comprehensive Checkstyle & Javadoc Audit: Audit all classes to ensure every method has complete Javadoc comments and passes all local Checkstyle rules without warnings.

---

### 2. Stretch Goals (Scope Review)
We reviewed every stretch goal listed in our original scope document:

* Committed / In-Progress Stretch Goals:
  * Search and Filtering (Already Implemented): We already built keyword searching across both academic and hobby task chains in DutyRecommendation.searchTasks() and hooked it up to Option 7 in Main.java.
  * Point System (Attempting if MVP finishes on schedule): We plan to add a simple gamified point tracker where completing an academic task awards points based on its estimated hours (e.g., 10 points per hour), and taking a break with a hobby spends earned points. This directly reinforces the Work-Then-Play balance.

* Officially Dropped Stretch Goals:
  To prevent scope creep and ensure our data structure tests remain our top priority, we are officially dropping:
  * CSV Import/Export
  * Priority System (low/med/high)
  * Custom Break Lengths
  * Weekly Summaries
  * Task Sorting

---

### 3. Required New Tests (Methods & Bad-Input Cases)
We will add new test cases covering pointer edge cases, the search feature, and point calculation logic:

* LinkedChainTest (Generic Storage Tests):
  * testRemoveHeadNode: Normal test removing the very first item in the chain.
  * testRemoveMiddleAndLastNode: Normal test verifying pointer rewiring when deleting interior or tail nodes.
  * testRemoveFromEmptyChain: Bad input; verifies removing from an empty chain returns false or null without throwing a NullPointerException.
  * testRemoveNonExistentItem: Bad input; verifies searching for an item not in the chain returns false and leaves chain size unchanged.
  * testAddNullThrowsException: Bad input; verifies add(null) and addToEnd(null) throw IllegalArgumentException.
  * testGetInvalidIndex: Bad input; verifies negative indices and indices >= size throw IndexOutOfBoundsException.

* testDutyRecommendation (Search, Duplicates & Points):
  * testSearchKeywordFound: Normal search test matching tasks by course code, assignment name, category, or description.
  * testSearchKeywordNotFound: Bad input; verifies searching for a word not present returns a clean "No tasks found" message.
  * testSearchBlankQuery: Bad input; verifies null or whitespace queries return a prompt error rather than crashing.
  * testEarnPointsOnAcademicCompletion: Normal test verifying points increase proportionately when homework is completed.
  * testSpendPointsOnHobbyCompletion: Normal test verifying points decrease when a hobby is finished.
  * testSpendPointsInsufficientBalance: Bad input; verifies a user cannot complete a hobby requiring more points than their current earned balance.

---

### 4. Team Ownership & Responsibilities
Each team member is assigned primary ownership for remaining MVP deliverables, tests, and documentation:

| Team Member | Assigned Responsibilities | Primary Deliverables / Test Files |
| :--- | :--- | :--- |
| Francis | LinkedChain Unit Tests | Build LinkedChainTest.java covering head, middle, and tail removals, empty chains, and null insertions. |
| Irmuun | Iterator Optimization & Search Tests | Update DutyRecommendation.java to use iterators instead of index loops, and write testDutyRecommendation search tests. |
| Chandrika | Point System & UI Polish | Implement the optional point ledger in DutyRecommendation and Main.java, write point tests, and perform final Checkstyle verifications. |
## Attribution
 a table with one row per class and test file listing who worked on it and how (wrote, revised, reviewed, tested, GenAI-assisted, …), plus a note on the way in which you used GenAI, if at all.
| Class | Who Worked on it | How |
| :--- | :---: | :--- |
| **AcademicTask**| Chandrika | Wrote the class and revised it |
| **AcademicTaskTest** | Chandrika | Wrote the test class and tested the class. GenAI assisted with making the test cases |
| **DutyRecommendation** | Irmuun | Wrote the class and reviewed it. GenAI assisted with the implementation of the class |
| **HobbyTask** | Chandrika | Wrote the class and revised it |
| **HobbyTaskTest** | Chandrika | Wrote the test class and tested the class. GenAI assisted with making the test cases |
| **LinkedChain** | Provided | N/A |
| **Main** | Francis | Wrote the Main class and reviewed it |
| **testDutyRecommendation** | Irmuun | Wrote the test class and tested the class. GenAI assisted with making the test cases |


## GenAI note

Our team did most of the work on this project ourselves, and we used GenAI (Claude) as a helper to touch up, finalize, and further sophisticate it. We wrote our own classes, our specification, and our README drafts. GenAI then helped us tighten them. It reviewed our specification, and several of the changes in our revised scope came from that review: validation in layers, equals and hashCode on the task classes, suggestNextHobby starting as false, adding new items to the front of the chain, and finishing a specific duplicate by its list position. We used it to generate our test cases, including edge cases like removing a task that isn’t there, completing the same task twice, and asking for a recommendation when no tasks are left. After that, we reviewed each test to make sure it checks real behavior before keeping it. It also helped us polish the wording of this README, and we checked everything against our code before using it. LinkedChain was provided to us and we did not modify it.
