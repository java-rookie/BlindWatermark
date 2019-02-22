/*
 * Copyright (c) 2019 ww23(https://github.com/ww23/BlindWatermark).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.ww23.image;

import me.ww23.image.coder.*;
import me.ww23.image.converter.Converter;
import me.ww23.image.converter.DctConverter;
import me.ww23.image.converter.DftConverter;


public class BlindWatermark {

    public static void main(String[] args) {

        if (args.length < 4) {
            help();
        }

        boolean isImage = false;
        Converter converter = null;
        String option = args[1].substring(1);

        if (option.contains("i")) {
            isImage = true;
        } else if (!option.contains("t")) {
            help();
        }

        if (option.contains("f")) {
            converter = new DftConverter();
        } else if (option.contains("c")) {
            converter = new DctConverter();
        } else {
            help();
        }

        switch (args[0]) {
            case "encode":
                Encoder encoder;
                if (isImage) {
                    encoder = new ImageEncoder(converter);
                } else {
                    encoder = new TextEncoder(converter);
                }
                encoder.encode(args[2], args[3], args[4]);
                break;
            case "decode":
                Decoder decoder;
                if (isImage) {
                    decoder = new ImageDecoder(converter);
                } else {
                    decoder = new TextDecoder(converter);
                }
                if (args.length == 5) {
                    decoder.decode(args[2], args[3], args[4]);
                } else {
                    decoder.decode(args[2], args[3]);
                }
                break;
            default:
                help();
        }
    }

    private static void help() {
        System.out.println("Usage: java -jar BlindWatermark.jar <commands> [args...] \n" +
                "   commands: \n" +
                "       encode <option> <image-src> <watermark-text> <image-encoded(text)>\n" +
                "       encode <option> <image-src> <watermark-image> <image-encoded(image)>\n" +
                "       decode <option> <image-encode(text)> <image-decode>\n" +
                "       decode <option> <image-src> <image-encoded(image)> <image-decode>\n" +
                "   options: \n" +
                "       -c discrete cosine transform\n" +
                "       -f discrete fourier transform\n" +
                "       -i image watermark\n" +
                "       -t text  watermark\n" +
                "   example: \n" +
                "       encode -ft foo.png test bar.png"
        );
        System.exit(-1);
    }
}