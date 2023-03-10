package com.example.api.bffmyapp.configuration.module;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.domain.*;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * Alternative au PageJacksonModule (org.springframework.cloud.openfeign.support) qui ne fonctionne pas pour
 * désérialiser correctement PageImpl (org.springframework.data.domain).
 * Ici les attributs "pageable" et "total" sont récupérés avant de construire PageImpl, ce qui n'est pas le cas
 * de PageJacksonModule.
 *
 * Structure qu'attend PageJacksonModule :
 *{
 * "content": [ ... ],
 * "number": 0,
 * "size": 10,
 * "totalElements": 23,
 * "sort": { ... }
 * }
 *
 * Structure qu'un client Feign Spring nous donne :
 *{
 * "content": [ ... ],
 * "pageable": {
 *   "page": 0,
 *   "size": 10,
 *   "sort": { ... },
 *   "unpaged": false,
 *   "paged": true
 * },
 * "total": 23,
 * "last": false,
 * "first": true,
 * "empty": false
 * }
 */
public class CustomPageModule extends Module {
    public CustomPageModule() {
    }

    public String getModuleName() {
        return "CustomPageModule";
    }

    public Version version() {
        return new Version(0, 1, 0, "", (String)null, (String)null);
    }

    public void setupModule(SetupContext context) {
        context.setMixInAnnotations(Page.class, PageMixIn.class);
    }

    static class SimplePageImpl<T> implements Page<T> {
        private final Page<T> delegate;

        SimplePageImpl(@JsonProperty("content") List<T> content,
                       @JsonProperty("pageable") JsonNode pageable,
                       @JsonProperty("total") @JsonAlias({"totalElements", "total-elements", "total_elements", "totalelements", "TotalElements"}) long total,
                       @JsonProperty("last") boolean last,
                       @JsonProperty("first") boolean first,
                       @JsonProperty("empty") boolean empty) {
            if (!pageable.isEmpty()) {
                var page = pageable.get("page").asInt();
                var size = pageable.get("size").asInt();

                this.delegate = new PageImpl(content, PageRequest.of(page, size), total);
            } else {
                this.delegate = new PageImpl(content);
            }
        }

        @JsonProperty
        public int getTotalPages() {
            return this.delegate.getTotalPages();
        }

        @JsonProperty
        public long getTotalElements() {
            return this.delegate.getTotalElements();
        }

        @JsonProperty
        public int getNumber() {
            return this.delegate.getNumber();
        }

        @JsonProperty
        public int getSize() {
            return this.delegate.getSize();
        }

        @JsonProperty
        public int getNumberOfElements() {
            return this.delegate.getNumberOfElements();
        }

        @JsonProperty
        public List<T> getContent() {
            return this.delegate.getContent();
        }

        @JsonProperty
        public boolean hasContent() {
            return this.delegate.hasContent();
        }

        @JsonIgnore
        public Sort getSort() {
            return this.delegate.getSort();
        }

        @JsonProperty
        public boolean isFirst() {
            return this.delegate.isFirst();
        }

        @JsonProperty
        public boolean isLast() {
            return this.delegate.isLast();
        }

        @JsonIgnore
        public boolean hasNext() {
            return this.delegate.hasNext();
        }

        @JsonIgnore
        public boolean hasPrevious() {
            return this.delegate.hasPrevious();
        }

        @JsonIgnore
        public Pageable nextPageable() {
            return this.delegate.nextPageable();
        }

        @JsonIgnore
        public Pageable previousPageable() {
            return this.delegate.previousPageable();
        }

        @JsonIgnore
        public <S> Page<S> map(Function<? super T, ? extends S> converter) {
            return this.delegate.map(converter);
        }

        @JsonIgnore
        public Iterator<T> iterator() {
            return this.delegate.iterator();
        }

        @Override
        @JsonIgnore
        public Pageable getPageable() {
            return this.delegate.getPageable();
        }

        @Override
        @JsonIgnore
        public boolean isEmpty() {
            return this.delegate.isEmpty();
        }

        public int hashCode() {
            return this.delegate.hashCode();
        }

        public boolean equals(Object obj) {
            return this.delegate.equals(obj);
        }

        public String toString() {
            return this.delegate.toString();
        }
    }

    @JsonDeserialize(
            as = SimplePageImpl.class
    )
    @JsonIgnoreProperties(
            ignoreUnknown = true
    )
    private interface PageMixIn {
    }
}
