import Trash from "../components/Trash.tsx";
import TrashCan from "../components/TrashCan.tsx";
import {TrashType} from "../types/TrashType.ts";
import {TrashCanType} from "../types/TrashCanType.ts";
import {DndContext, closestCenter, DragEndEvent} from "@dnd-kit/core";
import {useEffect, useState} from "react";
import axios from "axios";

export default function Game() {

    const [trashes, setTrashes] = useState<TrashType[]>([]);
    const [trashCans, setTrashCans] = useState<TrashCanType[]>([])
    const [dragResults, setDragResults] = useState<{
        [key: string]: { trashCanId: string; trashIds: string[] };
    }>({});

    const [initialTrashes, setInitialTrashes] = useState<boolean>(false);
    const [gameEnd, setGameEnd] = useState<boolean>(false);

    useEffect(() => {
        if (!initialTrashes) {
            getTrashes();
            setInitialTrashes(true);
        }
        getTrashCans();
    }, [initialTrashes]);

    function getTrashes() {
        axios.get("api/trash")
            .then(response => setTrashes((response.data)))
    }

    function getTrashCans() {
        axios.get("api/trashcan")
            .then(response => setTrashCans((response.data)))
    }

    function handleDragEnd(event: DragEndEvent) {
        const {active, over} = event

        if (active && over) {
            setDragResults((prevResults) => {
                const newResults = {...prevResults};
                newResults[over.id] = {
                    trashCanId: over.id as string,
                    trashIds: [...(newResults[over.id]?.trashIds || []), active.id as string],
                };
                return newResults;
            });
            setTrashes((prevTrashes) => {
                const trashToSort = prevTrashes.filter((trash) => trash.id !== active.id)
                handleGameEnd(trashToSort);
                return trashToSort;
            });
        }
    }

    function handleGameEnd(trashToSort: TrashType []) {
        if (trashToSort.length === 0) setGameEnd(true)
    }

    console.log(dragResults)

    return (
        <DndContext
            collisionDetection={closestCenter}
            onDragEnd={handleDragEnd}
        >
            {gameEnd ? (
                <div>
                    <h1>Well Done!</h1>
                    <p>All trash is sorted.</p>
                </div>
            ) : (
                <>
                    <Trash trashes={trashes}/>
                    <TrashCan trashCans={trashCans}/>
                </>)}
        </DndContext>
    )
}
