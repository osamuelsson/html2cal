package se.s8n.html2cal

@Grab(group='net.sourceforge.nekohtml', module='nekohtml', version='1.9.14')
import org.cyberneko.html.parsers.DOMParser
import org.w3c.dom.html.HTMLTableRowElement
import org.xml.sax.InputSource

/**
 */

// import groovy.util.XmlSlurper
def xmlSlurper = new XmlSlurper()
def neko = new DOMParser()
def file = new File("file.html")
println( file )
def inputStream = new FileInputStream(file)
InputSource is = new InputSource(inputStream)

neko.parse(is)
def document = neko.getDocument()

def body = document.firstChild.lastChild

println body
println body.nodeName

def font  = body.childNodes.findAll{ n -> println("hej " + n +  " hopp " + n.nodeName) ; println("CHILD: " + n.childNodes); n.nodeName == "FONT"}

def chi = font.get(0).childNodes.findAll{ it.nodeName == "FONT" }
def tab2 = chi.get(0).find({ it.nodeName == "TABLE" } ).childNodes.find( { it.nodeName == "TBODY" })
println("tab2: " + tab2)
def trs = tab2.childNodes.findAll{ it.nodeName == "TR" }

// println("trs: " + trs)

for (HTMLTableRowElement htmlTableRowElement: trs.childNodes) {
    def columns = htmlTableRowElement.getChildNodes()
    if (columns.length > 1) {
        def date = columns.item(1).firstChild.nodeValue
        def startTime = columns.item(3).firstChild.nodeValue
        if (startTime == null || "" == startTime || startTime.isAllWhitespace() ||startTime == " ") // TODO: check what char that is
            continue
        def endTime = columns.item(4).firstChild.nodeValue
        def location = columns.item(5).firstChild.nodeValue
        def breakTime = columns.item(6).firstChild.nodeValue
        /*
        for (int i = 0; i < columns.length; i++) {
            printf("%d: %s\n", [i, columns.item(i).firstChild.nodeValue])
        }
        */
        // printf("startTime is <<%s>> and that is %d long\n", [startTime, startTime.length()])
        printf("Work on %s from <%s> to %s at %s with %s break\n", [date, startTime, endTime, location, breakTime])
    }
}