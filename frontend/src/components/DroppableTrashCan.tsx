import styled from "styled-components";
import {useDroppable} from "@dnd-kit/core";
import {TrashCanType} from "../types/TrashCanType.ts";

type DroppableTrashCanProps = {
    trashCan: TrashCanType;
    isOver?: boolean;
}

export default function DroppableTrashCan(props: Readonly<DroppableTrashCanProps>) {

    const {isOver, setNodeRef} = useDroppable({
        id: props.trashCan.id,
    });

    const style = {
        transform: isOver ? 'scale(1.2)' : 'scale(1)', // Hier wird die Skalierung angewendet
    };

    return (
        <StyledDroppableTrashCan
            id={props.trashCan.id}
            ref={setNodeRef}
            style={style}>
            <img src={`${props.trashCan.image}`} alt={`${props.trashCan.name}`}/>
        </StyledDroppableTrashCan>
    )
}

type StyledDroppableTrashCanProps = {
    isOver?: boolean;
}

const StyledDroppableTrashCan = styled.div<StyledDroppableTrashCanProps>`
  height: auto;
  box-sizing: border-box;
  width: 33%;
  //width: 100px; 
  transition: transform 0.3s ease;
  transform: ${({isOver}) => isOver ? 'scale(1.2)' : 'scale(1)'};
`
