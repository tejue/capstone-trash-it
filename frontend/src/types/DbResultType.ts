export type DbResultType = {
    dbResult: DbResult[]
}

type DbResult = {
    trashCanId: string;
    trashIds: string[];
}