package com.jushi.library.lottie.parser;

import android.util.JsonReader;

import com.jushi.library.lottie.model.DocumentData;

import java.io.IOException;

public class DocumentDataParser implements ValueParser<DocumentData> {
  public static final DocumentDataParser INSTANCE = new DocumentDataParser();

  private DocumentDataParser() {}

  @Override public DocumentData parse(JsonReader reader, float scale) throws IOException {
    String text = null;
    String fontName = null;
    double size = 0;
    DocumentData.Justification justification = DocumentData.Justification.CENTER;
    int tracking = 0;
    double lineHeight = 0;
    double baselineShift = 0;
    int fillColor = 0;
    int strokeColor = 0;
    double strokeWidth = 0;
    boolean strokeOverFill = true;

    reader.beginObject();
    while (reader.hasNext()) {
      switch (reader.nextName()) {
        case "t":
          text = reader.nextString();
          break;
        case "f":
          fontName = reader.nextString();
          break;
        case "s":
          size = reader.nextDouble();
          break;
        case "j":
          int justificationInt = reader.nextInt();
          if (justificationInt > DocumentData.Justification.CENTER.ordinal() || justificationInt < 0) {
            justification = DocumentData.Justification.CENTER;
          } else {
            justification = DocumentData.Justification.values()[justificationInt];
          }
          break;
        case "tr":
          tracking = reader.nextInt();
          break;
        case "lh":
          lineHeight = reader.nextDouble();
          break;
        case "ls":
          baselineShift = reader.nextDouble();
          break;
        case "fc":
          fillColor = JsonUtils.jsonToColor(reader);
          break;
        case "sc":
          strokeColor = JsonUtils.jsonToColor(reader);
          break;
        case "sw":
          strokeWidth = reader.nextDouble();
          break;
        case "of":
          strokeOverFill = reader.nextBoolean();
          break;
        default:
          reader.skipValue();
      }
    }
    reader.endObject();

    return new DocumentData(text, fontName, size, justification, tracking, lineHeight,
        baselineShift, fillColor, strokeColor, strokeWidth, strokeOverFill);
  }
}
