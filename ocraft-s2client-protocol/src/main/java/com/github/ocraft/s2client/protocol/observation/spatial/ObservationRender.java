package com.github.ocraft.s2client.protocol.observation.spatial;

/*-
 * #%L
 * ocraft-s2client-protocol
 * %%
 * Copyright (C) 2017 - 2018 Ocraft Project
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import SC2APIProtocol.Spatial;
import com.github.ocraft.s2client.protocol.Strings;

import java.io.Serializable;

import static com.github.ocraft.s2client.protocol.DataExtractor.tryGet;
import static com.github.ocraft.s2client.protocol.Errors.required;
import static com.github.ocraft.s2client.protocol.Preconditions.require;

public final class ObservationRender implements Serializable {

    private static final long serialVersionUID = -3164970033791841768L;

    private final ImageData map;
    private final ImageData minimap;

    private ObservationRender(Spatial.ObservationRender sc2ApiObservationRender) {
        map = tryGet(Spatial.ObservationRender::getMap, Spatial.ObservationRender::hasMap)
                .apply(sc2ApiObservationRender).map(ImageData::from).orElseThrow(required("map"));

        minimap = tryGet(Spatial.ObservationRender::getMinimap, Spatial.ObservationRender::hasMinimap)
                .apply(sc2ApiObservationRender).map(ImageData::from).orElseThrow(required("minimap"));
    }

    public static ObservationRender from(Spatial.ObservationRender sc2ApiObservationRender) {
        require("sc2api observation render", sc2ApiObservationRender);
        return new ObservationRender(sc2ApiObservationRender);
    }

    public ImageData getMap() {
        return map;
    }

    public ImageData getMinimap() {
        return minimap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObservationRender that = (ObservationRender) o;

        return map.equals(that.map) && minimap.equals(that.minimap);
    }

    @Override
    public int hashCode() {
        int result = map.hashCode();
        result = 31 * result + minimap.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Strings.toJson(this);
    }
}
