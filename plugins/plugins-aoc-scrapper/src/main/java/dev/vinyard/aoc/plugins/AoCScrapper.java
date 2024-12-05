package dev.vinyard.aoc.plugins;

import dev.vinyard.blueprinter.plugins.api.BluePrinterExtension;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.velocity.VelocityContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pf4j.Extension;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Extension
@Slf4j
public class AoCScrapper implements BluePrinterExtension {

    public static String getTitle(String url, String session, int index) throws IOException {
        Document doc = AoCScrapper.fetchDocument(url, session);

        Elements h2 = doc.select("html > body > main > article > h2");

        Pattern pattern = Pattern.compile("--- Day \\d+: (.*?) ---");

        Matcher matcher = pattern.matcher(h2.get(index).text());

        return Optional.of(matcher).filter(Matcher::find).map(m -> m.group(1)).orElse(h2.get(index).text());
    }

    public static String getDescription(String url, String session, int index) throws IOException {
        Document doc = AoCScrapper.fetchDocument(url, session);

        Elements elements = doc.select("html > body > main > article");

        String html = elements.get(index).html();

        CharStream charStream = CharStreams.fromString(html);

        DescriptionLexer lexer = new DescriptionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        DescriptionParser parser = new DescriptionParser(tokens);

        DescriptionParser.HtmlDocumentContext htmlDocument = parser.htmlDocument();

        HtmlListener htmlListener = new HtmlListener();

        return htmlListener.visitHtmlDocument(htmlDocument);
    }

    public static List<String> getInputs(String url, String session, int index) throws IOException {
        Document doc = AoCScrapper.fetchDocument(url, session);

        Elements elements = doc.select("html > body > main > article");

        Elements codes = elements.get(index).select("pre > code");

        return codes.stream().map(Element::text).toList();
    }

    public static String getInput(String url, String session) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder().url(url).addHeader("Cookie", "session=" + session).build();

        try (Response response = client.newCall(request).execute()) {
            return Objects.requireNonNull(response.body()).string();
        } finally {
            client.dispatcher().executorService().shutdown();
            client.connectionPool().evictAll();
        }
    }

    private static Document fetchDocument(String url, String session) throws IOException {
        return Jsoup.connect(url).cookie("session", session).get();
    }

    @Override
    public void init(VelocityContext velocityContext) {
        log.info("Adding aocScrapper to velocity context.");
        velocityContext.put("aocScrapper", this);
    }
}