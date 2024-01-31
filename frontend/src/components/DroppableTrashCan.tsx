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
        transform: isOver ? 'scale(1.2)' : 'scale(1)'
    };

    return (
        <StyledDroppableTrashCan
            id={props.trashCan.id}
            ref={setNodeRef}
            style={style}>
            <StyledImage src={`${props.trashCan.image}`} alt={`${props.trashCan.name}`}/>
        </StyledDroppableTrashCan>
    )
}

type StyledDroppableTrashCanProps = {
    isOver?: boolean;
}

const StyledDroppableTrashCan = styled.div<StyledDroppableTrashCanProps>`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  transform: ${({isOver}) => isOver ? 'scale(1.2)' : 'scale(1)'};
`

const StyledImage = styled.img`
  height: 100%;
  max-height: 100%;
  max-width: 100%;
  overflow: hidden;
`