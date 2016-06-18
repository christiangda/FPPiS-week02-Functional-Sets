package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

    val s4 = Set(1, 2, 3, 4, 5)
    val s5 = Set(3, 5, 6, 7, 8)
    val s6 = Set(6, 8, 9, 1000, 2000)

    val s7 = Set(-1, -2, -3, -4, -5)
    val s8 = Set(-3, -2, -1, 1, 2, 3)

    val s9 = Set(1, 2, 3, 4, 5) //
    val s10 = Set(2, 4, 6, 8, 10) // s9*2
    val s11 = Set(1, 4, 9, 16, 25) // s9*s9
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("singletonSet(4) contains 4") {
    new TestSets {
      assert(contains(s4, 4), "Singleton Fails in contains(s4, 4)")
    }
  }

  /**
   * Test: union
   */
  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union of {1} and {2} contains {1}")
      assert(contains(s, 2), "Union of {1} and {2} contains {2}")
      assert(!contains(s, 3), "Union of {1} and {2} not contains {3}")

      val ss = union(s3, s4)
      assert(contains(ss, 3), "Union of {3} and {1, 2, 3, 4, 5} contains {3}")
      assert(contains(ss, 4), "Union of {3} and {1, 2, 3, 4, 5} contains {4}")
      assert(contains(ss, 1), "Union of {3} and {1, 2, 3, 4, 5} contains {1}")
    }
  }

  /**
   * Test: intersect
   */
  test("intersect contains all elements of each set") {
    new TestSets {
      val ss = intersect(s3, s4)
      assert(contains(ss, 3), "Intersect of {3} and {1, 2, 3, 4, 5} contains {3}")

      val sss = intersect(s5, s6)
      assert(contains(sss, 6), "Intersect of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} contains {6}")
      assert(contains(sss, 8), "Intersect of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} contains {8}")
    }
  }

  test("intersect not contains any elements of each set") {
    new TestSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1), "Intersect of {1} and {2} not contains {1}")
      assert(!contains(s, 2), "Intersect of {1} and {2} not contains {2}")

      val ss = intersect(s3, s4)
      assert(!contains(ss, 2), "Intersect of {3} and {1, 2, 3, 4, 5} not contains {2}")
      assert(!contains(ss, 4), "Intersect of {3} and {1, 2, 3, 4, 5} not contains {4}")

      val sss = intersect(s5, s6)
      assert(!contains(sss, 3), "Intersect of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {3}")
      assert(!contains(sss, 4), "Intersect of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {4}")
      assert(!contains(sss, 5), "Intersect of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {5}")
      assert(!contains(sss, 7), "Intersect of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {7}")
    }
  }

  /**
   * Test: diff
   */
  test("diff contains all elements of each set") {
    new TestSets {
      val s = diff(s1, s2)
      assert(contains(s, 1), "Diff of {1} and {2} contains {1}")

      val ss = diff(s4, s3)
      assert(contains(ss, 1), "Diff of {3} and {1, 2, 3, 4, 5} contains {1}")
      assert(contains(ss, 2), "Diff of {3} and {1, 2, 3, 4, 5} contains {2}")
      assert(contains(ss, 4), "Diff of {3} and {1, 2, 3, 4, 5} contains {4}")
      assert(contains(ss, 5), "Diff of {3} and {1, 2, 3, 4, 5} contains {5}")

      val sss = diff(s5, s6)
      assert(contains(sss, 3), "Diff of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} contains {3}")
      assert(contains(sss, 5), "Diff of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} contains {5}")
      assert(contains(sss, 7), "Diff of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} contains {7}")
    }
  }

  test("diff not contains any elements of each set") {
    new TestSets {
      val s = diff(s1, s2)
      assert(!contains(s, 2), "Diff of {1} and {2} not contains {2}")

      val ss = diff(s3, s4)
      assert(!contains(ss, 1), "Diff of {3} and {1, 2, 3, 4, 5} not contains {1}")
      assert(!contains(ss, 2), "Diff of {3} and {1, 2, 3, 4, 5} not contains {2}")
      assert(!contains(ss, 3), "Diff of {3} and {1, 2, 3, 4, 5} not contains {3}")
      assert(!contains(ss, 4), "Diff of {3} and {1, 2, 3, 4, 5} not contains {4}")
      assert(!contains(ss, 5), "Diff of {3} and {1, 2, 3, 4, 5} not contains {5}")

      val sss = diff(s5, s6)
      assert(!contains(sss, 6), "Diff of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {6}")
      assert(!contains(sss, 8), "Diff of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {8}")
      assert(!contains(sss, 9), "Diff of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {9}")
      assert(!contains(sss, 1000), "Diff of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {1000}")
      assert(!contains(sss, 2000), "Diff of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {2000}")
    }
  }

  /**
   * Test: filter
   */
  test("filter contains all elements of ecah set") {
    new TestSets {
      val sss = diff(s5, s6)
      assert(contains(sss, 3), "Filter of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {3}")
      assert(contains(sss, 5), "Filter of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {5}")
      assert(contains(sss, 7), "Filter of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {7}")
    }
  }

  test("filter not contains any elements of ecah set") {
    new TestSets {
      val s = filter(s1, s2)
      assert(!contains(s, 1), "Filter of {1} and {2} contains {1}")
      assert(!contains(s, 2), "Filter of {1} and {2} contains {2}")

      val ss = filter(s3, s4)
      assert(!contains(ss, 1), "Filter of {3} and {1, 2, 3, 4, 5} not contains {1}")
      assert(!contains(ss, 2), "Filter of {3} and {1, 2, 3, 4, 5} not contains {2}")
      assert(!contains(ss, 4), "Filter of {3} and {1, 2, 3, 4, 5} not contains {4}")
      assert(!contains(ss, 5), "Filter of {3} and {1, 2, 3, 4, 5} not contains {5}")

      val sss = diff(s5, s6)
      assert(!contains(sss, 6), "Filter of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {6}")
      assert(!contains(sss, 8), "Filter of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {8}")
      assert(!contains(sss, 9), "Filter of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {9}")
      assert(!contains(sss, 1000), "Filter of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {1000}")
      assert(!contains(sss, 2000), "Filter of {3, 5, 6, 7, 8} and {6, 8, 9, 1000, 2000} not contains {2000}")
    }
  }

  /**
   * Test: forall
   */
  test("forall test suit") {
    new TestSets {
      assert(forall(s5, { e: Int => e > 0 }), "Forall valid if {3, 5, 6, 7, 8} are positive numbers")
      assert(forall(s7, { e: Int => e < 0 }), "Forall valid if {-1, -2, -3, -4, -5} are negative numbers")
      assert(forall(s8, { e: Int => e != 0 }), "Forall valid if {-3, -2, -1, 1, 2, 3} not contains zero (0)")
    }
  }

  /**
   * Test: exists
   */
  test("exists test suit") {
    new TestSets {
      assert(exists(s5, { e: Int => e > 0 }), "Exist valid if {3, 5, 6, 7, 8} contains positive numbers")
      assert(!exists(s5, { e: Int => e < 0 }), "Exist valid if {3, 5, 6, 7, 8} contains positive numbers")

      assert(exists(s5, s3), "Exist valid if {3, 5, 6, 7, 8} contains {3}")
      assert(exists(s5, s6), "Exist valid if {3, 5, 6, 7, 8} contains some of {6, 8, 9, 1000, 2000}")
      
    }
  }

  /**
   * Test: map
   */
  test("map test suit") {
    new TestSets {
      val m1 = map(s9, { a: Int => a * 2 })
      assert(exists(s9, s10), "Map valid if {1, 2, 3, 4, 5} exists in {2, 4, 6, 8, 10}")
      
      val m2 = map(s9, {a: Int => a * a})
      assert(exists(s9, s11), "Map valid if {1, 2, 3, 4, 5} exists in {1, 4, 9, 16, 25}")
   
      // Even numbers
      assert(forall(m1, {a:Int => (a % 2) == 0}))
    }
  }
}
