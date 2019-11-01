# groovy258-dsl-closure-workshop

* References:
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

# preface
* goals of this workshop:
    * formally introducing groovy's closure
    * understanding what is state machine and how it works
    * becoming acquainted with DSL and its 
    * how properly structure projects
    * how properly write tests
* workshop: `workshop` package, answers: `answers` package

# closure
* is **an open, anonymous, block of code** that can 
 take arguments, return a value and be assigned to a variable
* may reference variables declared in its surrounding scope
* in opposition to the formal definition of a closure, Closure in the Groovy language **can 
 also contain free variables which are defined outside of its surrounding 
 scope**.
* Groovy defines closures as instances of the Closure class. 
* very different from lambda expressions in Java 8
    * delegation is a key concept in Groovy closures which has no equivalent in lambdas
* closure defines 3 distinct properties:
    * **this** corresponds to the enclosing class where the closure is 
    defined
    * **owner** corresponds to the enclosing object where the closure is 
    defined, which may be either a class or a closure
    * **delegate** corresponds to a third party object where methods 
    calls or properties are resolved whenever the receiver of the message 
    is not defined
    * while closure-this and closure-owner refer to the lexical scope of a closure, the delegate is a user defined 
    object that a closure will use
    ```
    class X {
        def hello() {
            println "hello from X"
        }
  
        def hello2() {
            def hi = { hello() }
            hi()
        }
    }
    
    class Y {
        static def handle(closure) {
            X x = new X()
            closure.delegate = x
            closure()
        }
    
        static void main(String[] args) {
            new X().hello2() // "hello from X"
            Y.handle { hello() } // "hello from X"
        }
    }
    ```
* `closure.rehydrate(delegate, owner, thisObject)` - returns a copy of this closure 
for which the `delegate`, `owner` and `thisObject` are replaced with the supplied 
parameters

## resolving strategies
* **Note that local variables are always looked up first, 
independently of the resolution strategy.**
* **OWNER_FIRST** - the closure will attempt 
to resolve property references and methods to the owner first, then 
the delegate - **this is the default strategy**.
* **DELEGATE_FIRST** - the closure will attempt to resolve property 
references and methods to the delegate first then the owner.
* others covered in: https://github.com/mtumilowicz/groovy-closure-owner-delegate-this

# dsl
* **Domain-Specific Languages** are small languages, focused on a particular 
aspect of a software system. They allow business experts to read or write 
code without having to be  programming experts
* DSLs come in two main forms:
    * **external** - language that's parsed independently of the host general purpose 
    language, examples: `regular expressions` and `CSS`
    * **internal** - particular form of `API` in a host general purpose language, often 
    referred to as a fluent interface, examples: `Spock` and `Mockito`

* `Groovy` has many features that make it great for writing `DSLs`:
    * closures with delegates
    * parentheses and dots `(.)` are optional
        * `Groovy` allows you to omit the parentheses for top-level expressions
        * when a closure is the last parameter of a method call `list.each  { println it }`
        * in some cases parentheses are required:
            * making nested method calls
            * calling a method without parameters
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
    * the ability to overload operators: https://github.com/mtumilowicz/groovy-operators-overloading
    * metaprogramming: `methodMissing` and `propertyMissing` features
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

# state machine
* simplest infinite state machine: state: consecutive numbers, function: incrementation
* finite-state machine (FSM)
    * it is a model of computation based on a hypothetical machine made of one or more states
        * only one single state of this machine can be active at the same time
        * machine has to switch from one state to another in order to perform different actions
    * is a mathematical model of computation
    * is an abstract machine that can be in exactly one of a finite number of states at any given time
        * abstract machine is a theoretical model of a computer hardware or software system
        * abstract machine consists of a definition in terms of input, output, and the set of allowable operations 
        used to turn the former into the latter
    * can change from one state to another in response to some external inputs and/or a condition is satisfied
    * the change from one state to another is called a transition
    * is defined by a list of its states, its initial state, and the conditions for each transition 
* finite state machines are of two types
    * deterministic
        * each of its transitions is uniquely determined by its source state and input symbol
        * reading an input symbol is required for each state transition
    * non-deterministic - does not need to obey above restrictions
* has less computational power than some other models of computation such as the Turing machine
    * there are computational tasks that a Turing machine can do but a FSM cannot
* can be used to simulate sequential logic, or, in other words, to represent and control execution flow
* elementary example: 
    * coin-operated turnstile
    * states: locked, unlocked
    * transitions: 
        * coin -> unlock
        * pushing the arm of the unlocked turnstile -> lock