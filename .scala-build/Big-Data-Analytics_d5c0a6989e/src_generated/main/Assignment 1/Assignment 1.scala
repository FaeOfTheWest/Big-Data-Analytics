package Assignment$u00201


final class Assignment$u00201$_ {
def args = Assignment$u00201_sc.args$
def scriptPath = """Assignment 1/Assignment 1.sc"""
/*<script>*/

/*</script>*/ /*<generated>*//*</generated>*/
}

object Assignment$u00201_sc {
  private var args$opt0 = Option.empty[Array[String]]
  def args$set(args: Array[String]): Unit = {
    args$opt0 = Some(args)
  }
  def args$opt: Option[Array[String]] = args$opt0
  def args$: Array[String] = args$opt.getOrElse {
    sys.error("No arguments passed to this script")
  }

  lazy val script = new Assignment$u00201$_

  def main(args: Array[String]): Unit = {
    args$set(args)
    val _ = script.hashCode() // hashCode to clear scalac warning about pure expression in statement position
  }
}

export Assignment$u00201_sc.script as `Assignment 1`

