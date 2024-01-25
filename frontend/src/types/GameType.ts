import {DbResultType} from "./DbResultType.ts";

export type GameType = {
    gameId: string;
    gameResult: DbResultType;
    playerResult: DbResultType;
    dataResult: DbResultType;
}