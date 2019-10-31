# groovy258-dsl-closure-workshop

* https://groovy-lang.org/closures.html
* https://github.com/mtumilowicz/groovy-closure-owner-delegate-this
* http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html
* https://github.com/PolomskiBartlomiej/groovy-dsl-statemachine
* https://github.com/mtumilowicz/groovy-dsl
* https://www.techopedia.com/definition/16447/state-machine
* https://en.wikipedia.org/wiki/Finite-state_machine
* https://en.wikipedia.org/wiki/Model_of_computation
* https://en.wikipedia.org/wiki/Abstract_machine
* https://en.wikipedia.org/wiki/Deterministic_finite_automaton
* https://en.wikipedia.org/wiki/Nondeterministic_finite_automaton
* https://medium.com/@mlbors/what-is-a-finite-state-machine-6d8dec727e2c
* simplest infinite state machine: state: consecutive numbers, function: incrementation

# preface
* goals of this workshop:
    * formally introducing groovy's closure
    * understanding what is state machine and how it works
    * becoming acquainted with DSL and its 
    * how properly structure projects
    * how properly write tests
* workshop: `workshop` package, answers: `answers` package

## closure
 A closure in Groovy is **an open, anonymous, block of code** that can 
 take arguments, return a value and be assigned to a variable. A closure 
 may reference variables declared in its surrounding scope. In opposition to 
 the formal definition of a closure, Closure in the Groovy language **can 
 also contain free variables which are defined outside of its surrounding 
 scope**. While breaking the formal concept of a closure, it offers a 
 variety of advantages which are described in this chapter.
 
  Groovy defines closures as instances of the Closure class. It makes 
  it very different from lambda expressions in Java 8. Delegation is a 
  key concept in Groovy closures which has no equivalent in lambdas.

 * **this** corresponds to the enclosing class where the closure is 
 defined.
 * **owner** corresponds to the enclosing object where the closure is 
 defined, which may be either a class or a closure.
 * **delegate** corresponds to a third party object where methods 
 calls or properties are resolved whenever the receiver of the message 
 is not defined.

# resolving strategies
 ## summary
 **Note that local variables are always looked up first, 
 independently of the resolution strategy.**
 
 * **OWNER_FIRST** - the closure will attempt 
 to resolve property references and methods to the owner first, then 
 the delegate - **this is the default strategy**.
 
 * **DELEGATE_FIRST** - the closure will attempt to resolve property 
 references and methods to the delegate first then the owner.
 
 * others covered in: https://github.com/mtumilowicz/groovy-closure-owner-delegate-this

## delegate
Within `Groovy` you can take a closure as a parameter and then call it using a 
local variable as a delegate.
```
@ToString
class X {
    String value
}

class Y {
    static def handler(closure) {
        X x = new X()
        closure.delegate = x
        closure()
        x
    }
}
```
```
println Y.handler {setValue 'test'} // X(test)
```

## dsl
**Domain-Specific Languages** are small languages, focused on a particular 
aspect of a software system. They allow business experts to read or write 
code without having to be  programming experts.  
DSLs come in two main forms:
* **external** - language that's parsed independently of the host general purpose 
language, examples: `regular expressions` and `CSS`.
* **internal** - particular form of `API` in a host general purpose language, often 
referred to as a fluent interface, examples: `Spock` and `Mockito`.

`Groovy` has many features that make it great for writing `DSLs`:
* [Closures](http://groovy-lang.org/closures.html) with [delegates](http://groovy-lang.org/closures.html#_delegate_of_a_closure).
* Parentheses and dots `(.)` are optional.
* The ability to [overload operators](http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html#_operator_overloading).
* [Metaprogramming](http://groovy-lang.org/metaprogramming.html): `methodMissing` and 
`propertyMissing` features.

### optional parentheses and dots
In `Groovy` it's possible to omit parentheses and dots
```
X.resolve {take 10 plus 30 minus 15} // it's same as: new X().take(10).plus(30).minus(15)
```
where:
```
class X {
    @Delegate
    Integer value
    
    Integer take(Integer x) {
        x
    }
    
    static def resolve(Closure closure) {
        closure.delegate = new X()
        closure()
    }
}
```

### metaprogramming (missing methods and properties)
`Groovy` provides a way to implement functionality at runtime via the methods:
* `methodMissing(String name, args)` - invoked only in the case of a 
failed method dispatch when no method can be found for the given name and/or 
the given arguments.
* `propertyMissing(String name)` - called only when no getter method for 
the given property can be found at runtime.
* `propertyMissing(String name, Object value)` - called only when no setter
method for the given property can be found at runtime.
```
class X {
    def methodMissing(String name, args) {
        println "methodMissing: $name $args"
    }

    def propertyMissing(String name, Object value) {
        println "propertyMissing: $name $value"
    }

    def propertyMissing(String name) {
        println "propertyMissing: $name"
    }
}
```
```
def x = new X()
x.nonExsistingMethod "1", "2", "3" // methodMissing: nonExsistingMethod [1, 2, 3]
x.nonExsistingProperty // propertyMissing: nonExsistingProperty
x.settingNonExsistingProperty = 5 // "propertyMissing: settingNonExsistingProperty 5"
```

## state machine
A finite-state machine (FSM) or finite-state automaton (FSA, plural: automata), finite automaton, or simply 
a state machine, is a mathematical model of computation. It is an abstract machine that can be in exactly one 
of a finite number of states at any given time. The FSM can change from one state to another in response to 
some external inputs and/or a condition is satisfied; the change from one state to another is called a 
transition.[1] An FSM is defined by a list of its states, its initial state, and the conditions for each 
transition. Finite state machines are of two types – deterministic finite state machines and non-deterministic 
finite state machines.

The finite state machine has less computational power than some other models of computation such as the Turing 
machine.[3] The computational power distinction means there are computational tasks that a Turing machine can do 
but a FSM cannot.

 a finite state machine is called a deterministic finite automaton (DFA), if

each of its transitions is uniquely determined by its source state and input symbol, and
reading an input symbol is required for each state transition.
A nondeterministic finite automaton (NFA), or nondeterministic finite state machine, does not need to obey these restrictions.

An abstract machine, also called an abstract computer, is a theoretical model of a computer hardware or software system

A typical abstract machine consists of a definition in terms of input, output, and the set of allowable operations used to turn the former into the latter.

A Finite State Machine, or FSM, is a computation model that can be used to simulate sequential logic, or, in other words, to represent and control execution flow.

A Finite State Machine is a model of computation based on a hypothetical machine made of one or more states. Only one single state of this machine can be active at the same time. It means the machine has to transition from one state to another in to perform different actions.

Coin-operated turnstile
States: locked, unlocked
Transitions: pointing a coin in the slot will unlock the turnstile, pushing the arm of the unlocked turnstile will let the costumer pass and lock the turnstile again

# project
* `closure.rehydrate(delegate, owner, thisObject)` - returns a copy of this closure 
for which the `delegate`, `owner` and `thisObject` are replaced with the supplied 
parameters.

*  metaprogramming:  
    we dynamically add sections with custom names and bodies
    ```
    def methodMissing(String methodName, args) {
        def section = new ToDo(title: methodName, body: args[0])
        toDo << section
    }    
    ```
* optional parentheses:
    ```
    DeadlineMemo.make {
        title 'IMPORTANT'
        deadline '2020-01-01'
        idea 'Be a better programmer!'
        plan 'Commit to github everyday!'
        xml
    }