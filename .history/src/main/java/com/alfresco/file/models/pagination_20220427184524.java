package com.alfresco.file.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class pagination {
    private String count;
        private boolean hasMoreItems;
            private int totalItems;
            private int skipCount;
            private int maxItems;
}
