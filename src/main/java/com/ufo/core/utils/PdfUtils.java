package com.ufo.core.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.ufo.core.context.ApplicationContext;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 类名称:
 * 类描述:
 * <p/>
 * 创建人: hekang
 * 创建时间: 上午11:25
 *
 * @verion 1.0
 */
public class PdfUtils {

    public static String IMG_PATH = "";

    public static String RELATIVE_PATH = "";

    static {
        IMG_PATH = ApplicationContext.getInstance().getRealPath() + "WEB-INF" +  System.getProperty("file.separator") + "classes" +  System.getProperty("file.separator") + "pdf" + System.getProperty("file.separator") + "images" + System.getProperty("file.separator");
        RELATIVE_PATH = ApplicationContext.getInstance().getRealPath() + "WEB-INF" +  System.getProperty("file.separator") + "classes" + System.getProperty("file.separator") + "html" + System.getProperty("file.separator");
    }

    public static void writeFromString(String path, String content) throws IOException, DocumentException {
        // step 1
        Document document = new Document(PageSize.A4);
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        // step 3
        document.open();
        // step 4

        // CSS
        CSSResolver cssResolver =
                XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
        
        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new AbstractImageProvider() {
            public String getImageRootPath() {
                return IMG_PATH;
            }
        });

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(content.getBytes()));

        // step 5
        document.close();
    }

    public static void writeFromTemplate(String path, String templateFilePath) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        // step 3
        document.open();
        // step 4

        // CSS
        CSSResolver cssResolver =
                XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new AbstractImageProvider() {
            public String getImageRootPath() {
                return IMG_PATH;
            }
        });

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new FileInputStream(templateFilePath));

        // step 5
        document.close();
    }
}
