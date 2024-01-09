import { ActivatedRoute } from "@angular/router";

export class ActionSystemModel {

    public static LIST = 'LISTAR';
    public static ADD = 'INCLUIR';
    public static ALTER = 'ALTERAR';
    public static VIEW = 'VISUALIZAR';

    private currentAction: string;

    constructor(route: ActivatedRoute) {
        this.currentAction = route.snapshot.data['currentAction'];
    }

    public isAdd(): boolean {
        return ActionSystemModel.ADD === this.currentAction;
    }

    public isAlter(): boolean {
        return ActionSystemModel.ALTER === this.currentAction
    }

    public isView(): boolean {
        return ActionSystemModel.VIEW === this.currentAction;
    }

    public isList(): boolean {
        return ActionSystemModel.LIST === this.currentAction;
    }

}
