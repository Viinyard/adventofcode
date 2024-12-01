package dev.vinyard.aoc.plugins;


import java.util.Optional;
import java.util.stream.Collectors;

public class HtmlListener extends DescriptionParserBaseVisitor<String> {

    @Override
    public String visitHtmlDocument(DescriptionParser.HtmlDocumentContext ctx) {
        return super.visitHtmlDocument(ctx);
    }

    @Override
    public String visitScriptletOrSeaWs(DescriptionParser.ScriptletOrSeaWsContext ctx) {
        return null;
    }

    @Override
    public String visitHtmlElements(DescriptionParser.HtmlElementsContext ctx) {
        return super.visitHtmlElements(ctx);
    }

    @Override
    public String visitHtmlElement(DescriptionParser.HtmlElementContext ctx) {
        String tag = ctx.tagName.getText();
        return switch (tag) {
            case "h2" -> String.format("<h2>%s</h2>\n", super.visitHtmlElement(ctx));
            case "pre" -> String.format("<pre>\n%s</pre>\n", super.visitHtmlElement(ctx));
            case "p" -> String.format("<p>%s</p>\n", super.visitHtmlElement(ctx));
            case "em" -> String.format("<b>%s</b>", super.visitHtmlElement(ctx));
            case "a" ->
                    String.format("<a %s>%s</a>", ctx.htmlAttribute().stream().map(this::visitHtmlAttribute).collect(Collectors.joining()), this.visitHtmlContent(ctx.htmlContent()));
            case "ul" -> String.format("<ul>%s</ul>\n", super.visitHtmlElement(ctx));
            case "li" -> String.format("<li>%s</li>", super.visitHtmlElement(ctx));
            default -> super.visitHtmlElement(ctx);
        };
    }

    @Override
    public String visitHtmlContent(DescriptionParser.HtmlContentContext ctx) {
        return super.visitHtmlContent(ctx);
    }

    @Override
    public String visitHtmlAttribute(DescriptionParser.HtmlAttributeContext ctx) {
        String tagName = ctx.tagName.getText();
        return switch (tagName) {
            case "href" -> String.format("%s=%s", ctx.tagName.getText(), ctx.value.getText());
            default -> "";
        };
    }

    @Override
    public String visitHtmlChardata(DescriptionParser.HtmlChardataContext ctx) {
        return ctx.getText();
    }

    @Override
    public String visitHtmlMisc(DescriptionParser.HtmlMiscContext ctx) {
        return "";
    }

    @Override
    public String visitHtmlComment(DescriptionParser.HtmlCommentContext ctx) {
        return "";
    }

    @Override
    public String visitScript(DescriptionParser.ScriptContext ctx) {
        return "";
    }

    @Override
    public String visitStyle(DescriptionParser.StyleContext ctx) {
        return "";
    }

    @Override
    protected String aggregateResult(String aggregate, String nextResult) {
        return String.join("", Optional.ofNullable(aggregate).orElse(""), Optional.ofNullable(nextResult).orElse(""));
    }
}
