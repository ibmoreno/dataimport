import { MatPaginatorIntl } from '@angular/material/paginator';

/**
 * Traduz a descrição do pagination
 */
export const LABEL_MAT_PAGINATOR_PT = () => {

    const paginatorIntl = new MatPaginatorIntl();
    paginatorIntl.previousPageLabel = 'Pagina Anterior';
    paginatorIntl.itemsPerPageLabel = 'Items por página:';

    paginatorIntl.nextPageLabel = 'Próxima página';
    paginatorIntl.firstPageLabel = 'Primeira Página';
    paginatorIntl.lastPageLabel = 'Ultima página';
    paginatorIntl.getRangeLabel = (page: number, pageSize: number, length: number) => {

        if (length === 0 || pageSize === 0) {
            return `0 de ${length}`;
        }

        length = Math.max(length, 0);

        const startIndex = page * pageSize;
        const endIndex = startIndex < length ? Math.min(startIndex + pageSize, length) : startIndex + pageSize;

        return `${startIndex + 1} - ${endIndex} de ${length}`;

    };

    return paginatorIntl;

}