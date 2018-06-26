import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'filter'
})
export class SearchPipe implements PipeTransform {

    transform(items: any[], searchText: string, data: any): any[] {
        console.log('Filter Date: ', data);
        // tslint:disable-next-line:curly
        if (!items) return [];
        // tslint:disable-next-line:curly
        if (!searchText) return items;

        searchText = searchText.toLowerCase();
        return items.filter( it => {
            return it.AIRID.toString().toLowerCase().indexOf(searchText) > -1;
        });
    }
}
