import styled from "styled-components";
import {useDroppable} from "@dnd-kit/core";
import {TrashCanType} from "../types/TrashCanType.ts";

type DroppableTrashCanProps = {
    trashCan: TrashCanType;
}

export default function DroppableTrashCan(props: Readonly<DroppableTrashCanProps>) {

    const {isOver, setNodeRef} = useDroppable({
        id: props.trashCan.id,
    });

    const style = {
        opacity: isOver ? 1 : 0.5,
    }

    return (
        <StyledDroppableTrashCan
            id={props.trashCan.id}
            ref={setNodeRef}
            style={style}>
            <img src={`${props.trashCan.image}`} alt={`${props.trashCan.name}`} />
        </StyledDroppableTrashCan>
    )
}

const StyledDroppableTrashCan = styled.div`
  height: auto;
  box-sizing: border-box;
  width: 33%;
  //width: 100px;  
`
