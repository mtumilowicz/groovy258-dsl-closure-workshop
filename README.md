# groovy258-dsl-closure-workshop

* https://groovy-lang.org/closures.html
* https://github.com/mtumilowicz/groovy-closure-owner-delegate-this
* http://docs.groovy-lang.org/docs/latest/html/documentation/core-domain-specific-languages.html
* https://github.com/PolomskiBartlomiej/groovy-dsl-statemachine
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