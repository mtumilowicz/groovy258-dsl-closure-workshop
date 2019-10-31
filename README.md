# groovy258-dsl-closure-workshop

* https://groovy-lang.org/closures.html
* https://github.com/mtumilowicz/groovy-closure-owner-delegate-this
* http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html
* https://github.com/PolomskiBartlomiej/groovy-dsl-statemachine
* https://github.com/mtumilowicz/groovy-dsl
* https://www.techopedia.com/definition/16447/state-machine
* simplest infinite state machine: state: consecutive numbers, function: incrementation

# preface
* goals of this workshop:
    * formally introducing groovy's closure
    * understanding what is state machine and how it works
    * becoming acquainted with DSL and its 
    * how properly structure projects
    * how properly write tests
* workshop: `workshop` package, answers: `answers` package

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
## optional parentheses and dots
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
## metaprogramming (missing methods and properties)
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
# project
Elaborated above mechanisms used:
* closures with delegation:
    ```
    def static make(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = DeadlineMemo) Closure closure) {
        def code = closure.rehydrate(new DeadlineMemo(), this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }
    ```
    * `@DelegatesTo` - for type checking
    * `closure.rehydrate(new DeadlineMemo(), this, this)` - returns a copy of this closure 
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
 ## definition
 A closure in Groovy is **an open, anonymous, block of code** that can 
 take arguments, return a value and be assigned to a variable. A closure 
 may reference variables declared in its surrounding scope. In opposition to 
 the formal definition of a closure, Closure in the Groovy language **can 
 also contain free variables which are defined outside of its surrounding 
 scope**. While breaking the formal concept of a closure, it offers a 
 variety of advantages which are described in this chapter.
 
 ## syntax
 A closure definition follows this syntax:
 
 `{ [closureParameters -> ] statements }`
 
 Where `[closureParameters->]` is an optional comma-delimited list of 
 parameters, and statements are 0 or more Groovy statements.
 
 ## Groovy closures vs lambda expressions
 Groovy defines closures as instances of the Closure class. It makes 
 it very different from lambda expressions in Java 8. Delegation is a 
 key concept in Groovy closures which has no equivalent in lambdas.
 
 # Owner, delegate and this
 _Reference_: Thanks to https://github.com/JackKarichkovskiy for
 helping me with described below intricacies.
 
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
 
 * **OWNER_ONLY** - the closure will resolve property 
 references and methods to the owner only and not call the delegate 
 at all.
 
 * **DELEGATE_ONLY** - the closure will resolve property 
 references and methods to the delegate only and entirely bypass 
 the owner.
 
 * **TO_SELF** - the closure will resolve property references to 
 itself and go through the usual MetaClass look-up process. This 
 means that properties and methods are neither resolved from the 
 owner nor the delegate, but only on the closure object itself. 
 This allows the developer to override getProperty using 
 `ExpandoMetaClass` of the closure itself.
 
 * **Note that local variables are always looked up first, 
 independently of the resolution strategy.**